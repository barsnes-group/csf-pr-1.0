package probe.com.dal;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import probe.com.model.beans.DatasetBean;
import probe.com.model.beans.FractionBean;
import probe.com.model.beans.PeptideBean;
import probe.com.model.beans.ProteinBean;
import probe.com.model.beans.StandardProteinBean;

/**
 * @author Yehia Farag abstraction for database
 */
public class DataBase implements Serializable {

    private static final long serialVersionUID = 1L;
    private Connection conn = null;
    private final String url, dbName, driver, userName, password;
    private final DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
    private DecimalFormat df = null;

    /**
     * @param url database url
     * @param dbName database name
     * @param driver database driver
     * @param userName database username
     * @param password database password
     *
     */
    public DataBase(String url, String dbName, String driver, String userName, String password) {
        this.url = url;
        this.dbName = dbName;
        this.driver = driver;
        this.userName = userName;
        this.password = password;
    }
    /**
     * check the availability of a dataset
     *
     * @param datasetId
     * @return dataset if available and null if not
     */
    public  DatasetBean readyToRetrieveDataset(int datasetId) {
        PreparedStatement selectDatasetStat;

        String selectDatasetProt = "SELECT * FROM `experiments_table` WHERE `exp_id` = ?";

        DatasetBean dataset = new DatasetBean();

        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectDatasetStat = conn.prepareStatement(selectDatasetProt);
            selectDatasetStat.setInt(1, datasetId);
            ResultSet rs = selectDatasetStat.executeQuery();
            while (rs.next()) {

                int fractionsNumber = rs.getInt("fractions_number");
                dataset.setFractionsNumber(fractionsNumber);
                String uploadedBy = rs.getString("uploaded_by");
                dataset.setUploadedByName(uploadedBy);
                String name = rs.getString("name");
                dataset.setName(name);
                String species = rs.getString("species");
                dataset.setSpecies(species);
                String sampleType = rs.getString("sample_type");
                dataset.setSampleType(sampleType);
                String sampleProcessing = rs.getString("sample_processing");
                dataset.setSampleProcessing(sampleProcessing);
                String instrumentType = rs.getString("instrument_type");
                dataset.setInstrumentType(instrumentType);
                String fragMode = rs.getString("frag_mode");
                dataset.setFragMode(fragMode);
                int proteinsNumber = rs.getInt("proteins_number");
                dataset.setProteinsNumber(proteinsNumber);
                String email = rs.getString("email");
                dataset.setEmail(email);
                String publicationLink = rs.getString("pblication_link");
                dataset.setPublicationLink(publicationLink);
                int peptidesInclude = rs.getInt("peptide_file");
                dataset.setPeptidesInclude(peptidesInclude);
                int peptidesNumber = rs.getInt("peptides_number");
                dataset.setPeptidesNumber(peptidesNumber);
                dataset.setDatasetId(datasetId);

            }
            rs.close();
            System.gc();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
        return dataset;

    }

    /**
     * check the fraction number of a dataset
     *
     * @param datasetId
     * @return dataset if available and null if not
     */
    public  int getFractionNumber(int datasetId) {
        PreparedStatement selectDatasetStat ;
        int fractionNumber = 0;
        String selectDataset = "SELECT `fractions_number` FROM `experiments_table` WHERE `exp_id` = ?;";

        try {
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectDatasetStat = conn.prepareStatement(selectDataset);
            selectDatasetStat.setInt(1, datasetId);
            ResultSet rs = selectDatasetStat.executeQuery();
            while (rs.next()) {
                fractionNumber = rs.getInt("fractions_number");
            }
            rs.close();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return fractionNumber;
    }

    /**
     * check the peptide availability for special peptide sequence a dataset
     *
     * @param sequence
     * @return test boolean
     */
    public  boolean checkPeptideAvailability(String sequence) {
        PreparedStatement selectDatasetStat;
        boolean test = false;
        String selectDatasetProt = "SELECT `sequence` FROM `proteins_peptides_table` WHERE `sequence`=?";
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectDatasetStat = conn.prepareStatement(selectDatasetProt);
            selectDatasetStat.setString(1, sequence);
            ResultSet rs = selectDatasetStat.executeQuery();
            while (rs.next()) {
                test = true;
            }
            rs.close();

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return test;
    }

    // RETRIVEING DATA
    /**
     * get the available datasets
     *
     * @return datasetsList
     */
    public  Map<Integer, DatasetBean> getDatasets()//get experiments list
    {
        PreparedStatement selectDatasetListStat;
        Map<Integer, DatasetBean> datasetList = new HashMap<Integer, DatasetBean>();
        Map<Integer, DatasetBean> tempDatasetList = new HashMap<Integer, DatasetBean>();
        String selectselectDatasetList = "SELECT * FROM `experiments_table` ;";
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectDatasetListStat = conn.prepareStatement(selectselectDatasetList);
            ResultSet rs = selectDatasetListStat.executeQuery();
            while (rs.next()) {
                DatasetBean dataset = new DatasetBean();
                int fractionsNumber = rs.getInt("fractions_number");
                dataset.setFractionsNumber(fractionsNumber);
                String uploadedBy = rs.getString("uploaded_by");
                dataset.setUploadedByName(uploadedBy);
                String name = rs.getString("name");
                dataset.setName(name);
                String species = rs.getString("species");
                dataset.setSpecies(species);
                String sampleType = rs.getString("sample_type");
                dataset.setSampleType(sampleType);
                String sampleProcessing = rs.getString("sample_processing");
                dataset.setSampleProcessing(sampleProcessing);
                String instrumentType = rs.getString("instrument_type");
                dataset.setInstrumentType(instrumentType);
                String fragMode = rs.getString("frag_mode");
                dataset.setFragMode(fragMode);
                int proteinsNumber = rs.getInt("proteins_number");
                dataset.setProteinsNumber(proteinsNumber);
                String email = rs.getString("email");
                dataset.setEmail(email);
                String publicationLink = rs.getString("pblication_link");
                dataset.setPublicationLink(publicationLink);
                int peptidesInclude = rs.getInt("peptide_file");
                dataset.setPeptidesInclude(peptidesInclude);
                int peptidesNumber = rs.getInt("peptides_number");
                dataset.setPeptidesNumber(peptidesNumber);
                int datasetId = rs.getInt("exp_id");
                dataset.setDatasetId(datasetId);
                String desc = rs.getString("description");
                dataset.setDescription(desc);
                int datasetType = rs.getInt("exp_type");
                dataset.setDatasetType(datasetType);
                dataset.setNumberValidProt(rs.getInt("valid_prot"));
                datasetList.put(dataset.getDatasetId(), dataset);
            }
            rs.close();
            for (int datasetId : datasetList.keySet()) {
                DatasetBean dataset = datasetList.get(datasetId);
                List<Integer> fractionIds = this.getFractionIdsList(datasetId);
                dataset.setFractionIds(fractionIds);
                tempDatasetList.put(datasetId, dataset);

            }
            datasetList.clear();
            datasetList.putAll(tempDatasetList);
            tempDatasetList.clear();

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
        System.gc();
        return datasetList;

    }

    /**
     * get selected dataset
     *
     * @param datasetId
     * @return dataset
     */
    public  DatasetBean getStoredDataset(int datasetId) {
        DatasetBean dataset = new DatasetBean();
        dataset.setDatasetId(datasetId);
        dataset = this.getDatasetDetails(dataset);
        dataset.setFractionsList(this.getFractionsList(dataset.getDatasetId()));
        dataset.setProteinList(this.getDatasetProteinsList(datasetId));	   	//get protein details	
        dataset.setPeptideList(this.getDatasetPeptidesList(datasetId));
        System.gc();
        return dataset;
    }

    /**
     * get fractions Ids list for a dataset
     *
     * @param datasetId
     * @return list of fraction Id's list
     */
    private  List<Integer> getFractionIdsList(int datasetId) {
        PreparedStatement selectDatasetFractionStat ;
        String selectDatasetFraction = "SELECT `fraction_id` FROM `experiment_fractions_table` WHERE `exp_id`=?;";
        List<Integer> fractionList = new ArrayList<Integer>();
        try {
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectDatasetFractionStat = conn.prepareStatement(selectDatasetFraction);
            selectDatasetFractionStat.setInt(1, datasetId);
            ResultSet rs = selectDatasetFractionStat.executeQuery();
            while (rs.next()) {
                fractionList.add(rs.getInt("fraction_id"));
            }
            rs.close();

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }
        return fractionList;
    }

    /**
     * get dataset details
     *
     * @param dataset
     * @return list of fraction Id's list
     */
    private  DatasetBean getDatasetDetails(DatasetBean dataset) {
        PreparedStatement selectDatasetStat ;
        String selectDataset = "SELECT * FROM `experiments_table` WHERE `exp_id`=? ;";
        try {
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectDatasetStat = conn.prepareStatement(selectDataset);
            selectDatasetStat.setInt(1, dataset.getDatasetId());
            ResultSet rs = selectDatasetStat.executeQuery();
            while (rs.next()) {
                int fractionsNumber = rs.getInt("fractions_number");
                dataset.setFractionsNumber(fractionsNumber);
                String uploadedBy = rs.getString("uploaded_by");
                dataset.setUploadedByName(uploadedBy);
                String name = rs.getString("name");
                dataset.setName(name);
                String species = rs.getString("species");
                dataset.setSpecies(species);
                String sampleType = rs.getString("sample_type");
                dataset.setSampleType(sampleType);
                String sampleProcessing = rs.getString("sample_processing");
                dataset.setSampleProcessing(sampleProcessing);
                String instrumentType = rs.getString("instrument_type");
                dataset.setInstrumentType(instrumentType);
                String fragMode = rs.getString("frag_mode");
                dataset.setFragMode(fragMode);
                int proteinsNumber = rs.getInt("proteins_number");
                dataset.setProteinsNumber(proteinsNumber);
                String email = rs.getString("email");
                dataset.setEmail(email);
                String publicationLink = rs.getString("pblication_link");
                dataset.setPublicationLink(publicationLink);
                int peptidesInclude = rs.getInt("peptide_file");
                dataset.setPeptidesInclude(peptidesInclude);
                int peptidesNumber = rs.getInt("peptides_number");
                dataset.setPeptidesNumber(peptidesNumber);
                String dec = rs.getString("description");
                dataset.setDescription(dec);
                int DatasetType = rs.getInt("exp_type");
                dataset.setDatasetType(DatasetType);
                dataset.setNumberValidProt(rs.getInt("valid_prot"));
            }
            rs.close();

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }
        return dataset;

    }

    /**
     * get dataset fractions list
     *
     * @param datasetId
     * @return fractions list for the selected dataset
     */
    public  Map<Integer, FractionBean> getFractionsList(int datasetId) {
        Map<Integer, FractionBean> fractionsList = new HashMap<Integer, FractionBean>();
        try {

            //get fractions id list
            PreparedStatement selectFractsListStat ;
            double minRange ;
            double maxRange ;
            String selectFractList = "SELECT `fraction_id`,`min_range` ,`max_range`,`index` FROM `experiment_fractions_table` where `exp_id` = ? ORDER BY `fraction_id`";
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectFractsListStat = conn.prepareStatement(selectFractList);
            selectFractsListStat.setInt(1, datasetId);
            ResultSet rs = selectFractsListStat.executeQuery();
            ArrayList<FractionBean> fractionIdList = new ArrayList<FractionBean>();
            FractionBean fb ;
            while (rs.next()) {
                fb = new FractionBean();
                int fraction_id = rs.getInt("fraction_id");
                fb.setFractionId(fraction_id);
                minRange = rs.getDouble("min_range");
                fb.setMinRange(minRange);
                maxRange = rs.getDouble("max_range");
                fb.setMaxRange(maxRange);
                int index = rs.getInt("index");
                fb.setFractionIndex(index);
                fractionIdList.add(fb);

            }
            rs.close();

            //get fractions 
            PreparedStatement selectFractsStat ;
            String selectFract = "SELECT * FROM `fractions_table` where `fraction_id` = ?";

            for (FractionBean fb2 : fractionIdList) {
                if (conn != null && !conn.isClosed()) {
                } else {
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                }
                selectFractsStat = conn.prepareStatement(selectFract);
                selectFractsStat.setInt(1, fb2.getFractionId());
                rs = selectFractsStat.executeQuery();
                Map<String, ProteinBean> proteinList = new HashMap<String, ProteinBean>();
                otherSymbols.setGroupingSeparator('.');
                df = new DecimalFormat("#.##", otherSymbols);

                while (rs.next()) {
                    ProteinBean pb = new ProteinBean();//fraction_id		  			
                    pb.setAccession(rs.getString("prot_accession"));
                    pb.setNumberOfPeptidePerFraction(rs.getInt("number_peptides"));
                    pb.setNumberOfSpectraPerFraction(rs.getInt("number_spectra"));
                    pb.setAveragePrecursorIntensityPerFraction(Double.valueOf(df.format(rs.getDouble("average_ precursor_intensity"))));
                    proteinList.put(pb.getAccession(), pb);
                }

                fb2.setProteinList(proteinList);
                fractionsList.put(fb2.getFractionId(), fb2);
                rs.close();

            }
            conn.close();

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (NumberFormatException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }

        System.gc();
        return fractionsList;

    }

    /**
     * get dataset peptides list
     *
     * @param datasetId
     * @return dataset peptide List
     */
    @SuppressWarnings("SleepWhileInLoop")
    public  Map<Integer, PeptideBean> getDatasetPeptidesList(int datasetId) {
        Map<Integer, PeptideBean> peptidesList = new HashMap<Integer, PeptideBean>();
        try {
            //get fractions id list
            PreparedStatement selectPeptideListStat ;
            String selectPeptideList = "SELECT `pep_id` FROM `experiment_peptides_table` WHERE `exp_id` = ?;";
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectPeptideListStat = conn.prepareStatement(selectPeptideList);
            selectPeptideListStat.setInt(1, datasetId);
            ResultSet rs = selectPeptideListStat.executeQuery();
            ArrayList<Integer> peptideIdList = new ArrayList<Integer>();
            while (rs.next()) {
                int peptideId = rs.getInt("pep_id");
                peptideIdList.add(peptideId);

            }
            rs.close();

            //get peptides 
            PreparedStatement selectPeptidesStat;
            String selectPeptide = "SELECT * FROM `proteins_peptides_table` WHERE `peptide_id` = ?;";
            int counter = 0;
            for (int pepId : peptideIdList) {

                PeptideBean pepb = new PeptideBean();
                pepb.setPeptideId(pepId);
                if (conn != null && !conn.isClosed()) {
                } else {
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                }
                selectPeptidesStat = conn.prepareStatement(selectPeptide);
                selectPeptidesStat.setInt(1, pepId);
                rs = selectPeptidesStat.executeQuery();

                while (rs.next()) {
                    pepb.setAaAfter(rs.getString("aa_after"));
                    pepb.setAaBefore(rs.getString("aa_before"));
                    pepb.setConfidence(rs.getDouble("confidence"));
                    pepb.setLocationConfidence(rs.getString("location_confidence"));
                    pepb.setNumberOfValidatedSpectra(rs.getInt("number_of_validated_spectra"));
                    pepb.setOtherProteinDescriptions(rs.getString("other_protein_description(s)"));
                    pepb.setOtherProteins(rs.getString("other_protein(s)"));
                    pepb.setPeptideEnd(rs.getString("peptide_end"));
                    pepb.setPeptideProteins((rs.getString("peptide_protein(s)")));
                    pepb.setPeptideProteinsDescriptions(rs.getString("peptide_proteins_description(s)"));
                    pepb.setPeptideStart(rs.getString("peptide_start"));
                    pepb.setPrecursorCharges(rs.getString("precursor_charge(s)"));
                    pepb.setMainProteinAccession(rs.getString("protein"));
                    pepb.setScore(rs.getDouble("score"));
                    pepb.setSequence(rs.getString("sequence"));
                    pepb.setVariableModification(rs.getString("variable_modification"));
                    pepb.setFixedModification(rs.getString("fixed_modification"));
                    pepb.setPeptideId(pepId);
                    pepb.setProteinInference(rs.getString("protein_inference"));
                    pepb.setSequenceTagged(rs.getString("sequence_tagged"));
                    pepb.setEnzymatic(Boolean.valueOf(rs.getString("enzymatic")));
                    pepb.setValidated(rs.getDouble("validated"));

                    pepb.setGlycopatternPositions(rs.getString("glycopattern_position(s)"));
                    String str = rs.getString("deamidation_and_glycopattern");
                    if (str != null && !str.equals("")) {
                        pepb.setDeamidationAndGlycopattern(Boolean.valueOf(str));
                    }
                    pepb.setLikelyNotGlycosite(Boolean.valueOf(rs.getString("likelyNotGlycosite")));

                    peptidesList.put(pepb.getPeptideId(), pepb);

                }
                rs.close();
                counter++;
                if (counter == 10000) {
                    conn.close();
                    Thread.sleep(100);
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                    counter = 0;
                }

            }

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InterruptedException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }
        System.gc();
        return peptidesList;
    }

    /**
     * get proteins map for especial dataset
     *
     * @param datasetId
     * @return proteinsList
     */
    public  Map<String, ProteinBean> getDatasetProteinsList(int datasetId) {
        Map<String, ProteinBean> proteinDatasetList = new HashMap<String, ProteinBean>();
        try {
            PreparedStatement selectProtDatasetStat ;
            String selectProtDataset = "SELECT * FROM `experiment_protein_table` WHERE `exp_id`=? ;";
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectProtDatasetStat = conn.prepareStatement(selectProtDataset);
            selectProtDatasetStat.setInt(1, datasetId);
            ResultSet rs = selectProtDatasetStat.executeQuery();
            while (rs.next()) {
                ProteinBean temPb = new ProteinBean();
                temPb.setAccession(rs.getString("prot_accession"));
                temPb.setOtherProteins(rs.getString("other_protein(s)"));
                temPb.setProteinInference(rs.getString("protein_inference_class"));
                temPb.setSequenceCoverage(rs.getDouble("sequence_coverage(%)"));
                temPb.setObservableCoverage(rs.getDouble("observable_coverage(%)"));
                temPb.setConfidentPtmSites(rs.getString("confident_ptm_sites"));
              
                temPb.setNumberValidatedPeptides(rs.getInt("number_validated_peptides"));
                temPb.setNumberValidatedSpectra(rs.getInt("number_validated_spectra"));
               
                temPb.setNsaf(rs.getDouble("nsaf"));
                temPb.setMw_kDa(rs.getDouble("mw_(kDa)"));
                temPb.setScore(rs.getDouble("score"));
                temPb.setConfidence(rs.getDouble("confidence"));
                temPb.setNonEnzymaticPeptides(Boolean.valueOf(rs.getString("non_enzymatic_peptides").toUpperCase()));
                temPb.setSpectrumFractionSpread_lower_range_kDa(rs.getString("spectrum_fraction_spread_lower_range_kDa"));
                temPb.setSpectrumFractionSpread_upper_range_kDa(rs.getString("spectrum_fraction_spread_upper_range_kDa"));
                temPb.setPeptideFractionSpread_lower_range_kDa(rs.getString("peptide_fraction_spread_lower_range_kDa"));
                temPb.setPeptideFractionSpread_upper_range_kDa(rs.getString("peptide_fraction_spread_upper_range_kDa"));

                temPb.setGeneName(rs.getString("gene_name"));
                temPb.setChromosomeNumber(rs.getString("chromosome_number"));

                temPb.setValidated(Boolean.valueOf(rs.getString("valid")));
                temPb.setDescription(rs.getString("description"));
                if (temPb.getOtherProteins() == null || temPb.getOtherProteins().equals("")) {
                    proteinDatasetList.put(temPb.getAccession(), temPb);
                } else {
                    proteinDatasetList.put(temPb.getAccession() + "," + temPb.getOtherProteins(), temPb);
                }
            }
            rs.close();

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }
        System.gc();
        return proteinDatasetList;
    }


    /**
     * Search for identification proteins by accession keywords
     *
     * @param searchSet set of query words
     * @param validatedOnly only validated proteins results
     * @return dataset Proteins Searching List
     */
    public Map<Integer, Map<Integer, ProteinBean>> searchIdentificationProteinAllDatasetsByAccession(Set<String> searchSet, boolean validatedOnly) {
        PreparedStatement selectProStat;
        String selectPro;

        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < searchSet.size(); x++) {
            if (x > 0) {
                sb.append(" OR ");
            }
            sb.append("`prot_key` LIKE (?)");

        }
        String sta = "";
        if (!sb.toString().trim().isEmpty()) {
            sta = "Where " + (sb.toString());
        }

        if (validatedOnly) {
            selectPro = "SELECT * FROM `experiment_protein_table`  " + sta + " AND `valid`=?;";
        } else {

            selectPro = "SELECT * FROM `experiment_protein_table` " + (sta);
        }
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectProStat = conn.prepareStatement(selectPro);
            int index = 1;
            for (String str : searchSet) {
                selectProStat.setString(index++, "%" + str.replace("'", "") + "%");
            }
            if (validatedOnly) {
                selectProStat.setString(index, "TRUE");
            }

            ResultSet rs = selectProStat.executeQuery();
            Map<Integer, Map<Integer, ProteinBean>> proteinsList = fillProteinInformation(rs, searchSet);
            return proteinsList;

        } catch (ClassNotFoundException e) {
            System.err.println("at error line 1595 " + this.getClass().getName() + "   " + e.getLocalizedMessage());
            return new HashMap<Integer, Map<Integer, ProteinBean>>();
        } catch (IllegalAccessException e) {
            System.err.println("at error line 1598 " + this.getClass().getName() + "   " + e.getLocalizedMessage());

            return new HashMap<Integer, Map<Integer, ProteinBean>>();
        } catch (InstantiationException e) {
            System.err.println("at error line 1602 " + this.getClass().getName() + "   " + e.getLocalizedMessage());

            return new HashMap<Integer, Map<Integer, ProteinBean>>();
        } catch (SQLException e) {
            System.err.println("at error line 1606 " + this.getClass().getName() + "   " + e.getLocalizedMessage());
            return new HashMap<Integer, Map<Integer, ProteinBean>>();
        }

    }

    /**
     * search for proteins by accession keywords
     *
     * @param accession array of query words
     * @param datasetId
     * @param validatedOnly only validated proteins results
     * @return dataset Proteins Searching List
     */
    public  Map<Integer, ProteinBean> searchProteinByAccession(String accession, int datasetId, boolean validatedOnly) {
        PreparedStatement selectProStat;
        String selectPro;
        if (validatedOnly) {
            selectPro = "SELECT * FROM `experiment_protein_table` Where `exp_id`=? AND  `prot_key` LIKE(?) AND `valid`=?;";
        } else {
            selectPro = "SELECT * FROM `experiment_protein_table` Where `exp_id`=? AND `prot_key` LIKE(?);";
        }

        Map<Integer, ProteinBean> protDatasetList = new HashMap<Integer, ProteinBean>();
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectProStat = conn.prepareStatement(selectPro);
            selectProStat.setString(2, "%" + accession + "%");
            selectProStat.setInt(1, datasetId);
            if (validatedOnly) {
                selectProStat.setString(3, "TRUE");
            }
            ResultSet rs = selectProStat.executeQuery();
            while (rs.next()) {
                ProteinBean temPb = new ProteinBean();
                temPb.setDatasetId(datasetId);
                temPb.setAccession(accession);
                temPb.setDescription(rs.getString("description"));
                temPb.setOtherProteins(rs.getString("other_protein(s)"));
                temPb.setProteinInference(rs.getString("protein_inference_class"));
                temPb.setSequenceCoverage(rs.getDouble("sequence_coverage(%)"));
                temPb.setObservableCoverage(rs.getDouble("observable_coverage(%)"));
                temPb.setConfidentPtmSites(rs.getString("confident_ptm_sites"));
                
                temPb.setNumberValidatedPeptides(rs.getInt("number_validated_peptides"));
                temPb.setNumberValidatedSpectra(rs.getInt("number_validated_spectra"));
                
                temPb.setNsaf(rs.getDouble("nsaf"));
                temPb.setMw_kDa(rs.getDouble("mw_(kDa)"));
                temPb.setScore(rs.getDouble("score"));
                temPb.setConfidence(rs.getDouble("confidence"));
                temPb.setNonEnzymaticPeptides(Boolean.valueOf(rs.getString("non_enzymatic_peptides").toUpperCase()));

                temPb.setSpectrumFractionSpread_lower_range_kDa(rs.getString("spectrum_fraction_spread_lower_range_kDa"));
                temPb.setSpectrumFractionSpread_upper_range_kDa(rs.getString("spectrum_fraction_spread_upper_range_kDa"));
                temPb.setPeptideFractionSpread_lower_range_kDa(rs.getString("peptide_fraction_spread_lower_range_kDa"));
                temPb.setPeptideFractionSpread_upper_range_kDa(rs.getString("peptide_fraction_spread_upper_range_kDa"));

                temPb.setGeneName(rs.getString("gene_name"));
                temPb.setChromosomeNumber(rs.getString("chromosome_number"));
                temPb.setValidated(Boolean.valueOf(rs.getString("valid")));
                temPb.setProtGroupId(rs.getInt("prot_group_id"));
                protDatasetList.put(temPb.getProtGroupId(), temPb);
            }
            rs.close();
            System.gc();
            return protDatasetList;

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

            return null;
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

            return null;
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

            return null;
        }

    }

    /**
     * get peptides list for giving ids
     *
     * @param peptideIds peptides IDs
     * @return peptides list
     */
    public  Map<Integer, PeptideBean> getPeptidesList(Set<Integer> peptideIds) {

        ResultSet rs ;
        // peptideIds.add(5741);
        Map<Integer, PeptideBean> peptidesList = new HashMap<Integer, PeptideBean>();
        try {

            PreparedStatement selectPeptidesStat ;
            String selectPeptide = "SELECT * FROM `proteins_peptides_table` WHERE  `peptide_id`=? ;";

            for (int pepId : peptideIds) {

                PeptideBean pepb = new PeptideBean();
                pepb.setPeptideId(pepId);
                if (conn != null && !conn.isClosed()) {
                } else {
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                }
                selectPeptidesStat = conn.prepareStatement(selectPeptide);
                selectPeptidesStat.setInt(1, pepId);
                rs = selectPeptidesStat.executeQuery();

                while (rs.next()) {
                    pepb.setAaAfter(rs.getString("aa_after"));
                    pepb.setAaBefore(rs.getString("aa_before"));
                    pepb.setConfidence(rs.getDouble("confidence"));
                    pepb.setLocationConfidence(rs.getString("location_confidence"));
                    pepb.setNumberOfValidatedSpectra(rs.getInt("number_of_validated_spectra"));
                    pepb.setOtherProteinDescriptions(rs.getString("other_protein_description(s)"));
                    pepb.setOtherProteins(rs.getString("other_protein(s)"));
                    pepb.setPeptideEnd(rs.getString("peptide_end"));
                    pepb.setPeptideProteins((rs.getString("peptide_protein(s)")));
                    pepb.setPeptideProteinsDescriptions(rs.getString("peptide_proteins_description(s)"));
                    pepb.setPeptideStart(rs.getString("peptide_start"));
                    pepb.setPrecursorCharges(rs.getString("precursor_charge(s)"));
                    pepb.setMainProteinAccession(rs.getString("protein"));
                    pepb.setScore(rs.getDouble("score"));
                    pepb.setSequence(rs.getString("sequence"));
                    pepb.setFixedModification(rs.getString("fixed_modification"));
                    pepb.setVariableModification(rs.getString("variable_modification"));
                    pepb.setProteinInference(rs.getString("protein_inference"));
                    pepb.setSequenceTagged(rs.getString("sequence_tagged"));
                    pepb.setEnzymatic(Boolean.valueOf(rs.getString("enzymatic")));
                    pepb.setValidated(rs.getDouble("validated"));
                    pepb.setPeptideId(pepId);
                    pepb.setGlycopatternPositions(rs.getString("glycopattern_position(s)"));
                    String str = rs.getString("deamidation_and_glycopattern");
                    if (str != null && !str.equals("")) {
                        pepb.setDeamidationAndGlycopattern(Boolean.valueOf(str));
                    }
                    pepb.setLikelyNotGlycosite(Boolean.valueOf(rs.getString("likelyNotGlycosite")));

                    peptidesList.put(pepb.getPeptideId(), pepb);

                }
                rs.close();

            }

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }
        System.gc();
        return peptidesList;
    }

    /**
     * get proteins fractions average list
     *
     * @param accession
     * @param datasetId
     * @return dataset peptide List
     */
    public  Map<Integer, FractionBean> getProteinFractionList(String accession, int datasetId) {
        Map<Integer, FractionBean> fractionsList = new HashMap<Integer, FractionBean>();
        try {
            //get fractions id list
            PreparedStatement selectFractsListStat ;
            double minRange = 0.0;
            double maxRange = 0.0;
            int index = 0;
            String selectFractList = "SELECT `fraction_id`,`min_range` ,`max_range`,`index` FROM `experiment_fractions_table` where `exp_id` = ?";
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectFractsListStat = conn.prepareStatement(selectFractList);
            selectFractsListStat.setInt(1, datasetId);
            ResultSet rs = selectFractsListStat.executeQuery();
            ArrayList<Integer> fractionIdList = new ArrayList<Integer>();
            while (rs.next()) {
                int fraction_id = rs.getInt("fraction_id");
                minRange = rs.getDouble("min_range");
                maxRange = rs.getDouble("max_range");
                index = rs.getInt("index");
                fractionIdList.add(fraction_id);

            }
            rs.close();
            //get fractions 
            PreparedStatement selectFractsStat ;
            String selectFract = "SELECT * FROM `fractions_table` where `fraction_id` = ? AND UPPER(`prot_accession`) LIKE UPPER(?) ";

            for (int fractId : fractionIdList) {

                FractionBean fb = new FractionBean();
                fb.setMinRange(minRange);
                fb.setMaxRange(maxRange);
                fb.setFractionId(fractId);
                fb.setFractionIndex(index);
                if (conn != null && !conn.isClosed()) {
                } else {
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                }
                selectFractsStat = conn.prepareStatement(selectFract);
                selectFractsStat.setInt(1, fractId);
                selectFractsStat.setString(2, accession);
                rs = selectFractsStat.executeQuery();
                Map<String, ProteinBean> proteinList = new HashMap<String, ProteinBean>();
                otherSymbols.setGroupingSeparator('.');
                df = new DecimalFormat("#.##", otherSymbols);
                while (rs.next()) {
                    ProteinBean pb = new ProteinBean();//fraction_id		  			
                    pb.setAccession(rs.getString("prot_accession"));
                    pb.setNumberOfPeptidePerFraction(rs.getInt("number_peptides"));
                    pb.setNumberOfSpectraPerFraction(rs.getInt("number_spectra"));
                    pb.setAveragePrecursorIntensityPerFraction(Double.valueOf(df.format(rs.getDouble("average_ precursor_intensity"))));
                    proteinList.put(pb.getAccession(), pb);
                }

                fb.setProteinList(proteinList);
                fractionsList.put(fb.getFractionId(), fb);
                rs.close();

            }

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (NumberFormatException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }
        System.gc();
        return fractionsList;

    }

    /**
     * search for proteins by protein description keywords
     *
     * @param protSearchKeyword array of query words
     * @param datasetId dataset Id
     * @param validatedOnly only validated proteins results
     * @return datasetProteinsSearchList
     */
    public  Map<Integer, ProteinBean> searchProteinByName(String protSearchKeyword, int datasetId, boolean validatedOnly) {
        PreparedStatement selectProStat;
        String selectPro ;
        Map<Integer, ProteinBean> proteinsList = new HashMap<Integer, ProteinBean>();

        if (validatedOnly) {
            selectPro = "SELECT * FROM `experiment_protein_table` WHERE `description` LIKE (?) AND `exp_id`=? AND `valid`=?;";
        } else {
            selectPro = "SELECT * FROM `experiment_protein_table` WHERE `description` LIKE (?) AND `exp_id`=? ";
        }
        try {
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectProStat = conn.prepareStatement(selectPro);
            selectProStat.setString(1, "%" + protSearchKeyword + "%");
            selectProStat.setInt(2, datasetId);
            if (validatedOnly) {
                selectProStat.setString(3, "TRUE");
            }
            ResultSet rs = selectProStat.executeQuery();
            int in = 1;
            while (rs.next()) {
                ProteinBean temPb = new ProteinBean();
                temPb.setDatasetId(datasetId);
                temPb.setAccession(rs.getString("prot_accession"));
                temPb.setDescription(rs.getString("description"));
                temPb.setOtherProteins(rs.getString("other_protein(s)"));
                temPb.setProteinInference(rs.getString("protein_inference_class"));
                temPb.setSequenceCoverage(rs.getDouble("sequence_coverage(%)"));
                temPb.setObservableCoverage(rs.getDouble("observable_coverage(%)"));
                temPb.setConfidentPtmSites(rs.getString("confident_ptm_sites"));
            
                temPb.setNumberValidatedPeptides(rs.getInt("number_validated_peptides"));
                temPb.setNumberValidatedSpectra(rs.getInt("number_validated_spectra"));
           
                temPb.setNsaf(rs.getDouble("nsaf"));
                temPb.setMw_kDa(rs.getDouble("mw_(kDa)"));
                temPb.setScore(rs.getDouble("score"));
                temPb.setConfidence(rs.getDouble("confidence"));
                temPb.setNonEnzymaticPeptides(Boolean.valueOf(rs.getString("non_enzymatic_peptides").toUpperCase()));

                temPb.setSpectrumFractionSpread_lower_range_kDa(rs.getString("spectrum_fraction_spread_lower_range_kDa"));
                temPb.setSpectrumFractionSpread_upper_range_kDa(rs.getString("spectrum_fraction_spread_upper_range_kDa"));
                temPb.setPeptideFractionSpread_lower_range_kDa(rs.getString("peptide_fraction_spread_lower_range_kDa"));
                temPb.setPeptideFractionSpread_upper_range_kDa(rs.getString("peptide_fraction_spread_upper_range_kDa"));

                temPb.setGeneName(rs.getString("gene_name"));
                temPb.setChromosomeNumber(rs.getString("chromosome_number"));
                temPb.setValidated(Boolean.valueOf(rs.getString("valid")));
                temPb.setProtGroupId(rs.getInt("prot_group_id"));
                proteinsList.put(temPb.getProtGroupId(), temPb);

            }
            rs.close();
            System.gc();
            return proteinsList;
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }

        System.gc();
        return null;
    }

    /**
     * search for proteins by peptide sequence keywords
     *
     * @param peptideSequenceKeyword array of query words
     * @param datasetId dataset Id
     * @param validatedOnly only validated proteins results
     * @return datasetProteinsSearchList
     */
    public  Map<Integer, ProteinBean> searchProteinByPeptideSequence(String peptideSequenceKeyword, int datasetId, boolean validatedOnly) {
        PreparedStatement selectProStat ;
        PreparedStatement selectPepIdStat ;
        Map<Integer, ProteinBean> proteinsList = new HashMap<Integer, ProteinBean>();
        List<Integer> pepIdList = new ArrayList<Integer>();
        String selectPepId = "SELECT `peptide_id`  FROM `proteins_peptides_table` WHERE `exp_id` = ? AND `sequence` = ? ;";
        Set<String> accessionList = new HashSet<String>();
        try {
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectPepIdStat = conn.prepareStatement(selectPepId);
            selectPepIdStat.setInt(1, datasetId);
            selectPepIdStat.setString(2, peptideSequenceKeyword);
            ResultSet rs = selectPepIdStat.executeQuery();
            while (rs.next()) {
                pepIdList.add(rs.getInt("peptide_id"));
            }
            rs.close();

            String selectPro = "SELECT `protein`  FROM `experiment_peptides_proteins_table`  WHERE `exp_id` = ? AND `peptide_id` = ? ;";

            for (int key : pepIdList) {
                if (conn != null && !conn.isClosed()) {
                } else {
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                }
                selectProStat = conn.prepareStatement(selectPro);
                selectProStat.setInt(1, datasetId);
                selectProStat.setInt(2, key);
                ResultSet rs_ = selectProStat.executeQuery();
                while (rs_.next()) {
                    accessionList.add(rs_.getString("protein"));
                }
                rs_.close();

                for (String accKey : accessionList) {
                    String[] AccArr = accKey.split(",");
                    for (String str : AccArr) {
                        if (str.length() > 3) {
                            Map<Integer, ProteinBean> tempProteinsList = this.searchProteinByAccession(str.trim(), datasetId, validatedOnly);
                            if (tempProteinsList != null) {
                                proteinsList.putAll(tempProteinsList);
                            }
                        }
                    }
                }

            }
            System.gc();
            return proteinsList;
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());

        }

        System.gc();
        return null;
    }

    /**
     * get users list
     *
     * @return list of users
     */
    public  Map<Integer, String> getUsersList() {
        Map<Integer, String> usersList = new HashMap<Integer, String>();
        PreparedStatement selectUsersStat ;

        try {
            String selectusers = "SELECT * FROM `users_table` WHERE `admin` = 'false'; ";
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectUsersStat = conn.prepareStatement(selectusers);
            ResultSet rs = selectUsersStat.executeQuery();
            while (rs.next()) {

                usersList.put(rs.getInt("id"), rs.getString("user_name"));

            }
            rs.close();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return usersList;//not valid
    }

    /**
     * remove username from the database
     *
     * @param username
     *
     * @return test boolean successful process
     */
    public  boolean removeUser(String username) {
        try {

            PreparedStatement removeUserStat ;
            String removeuser = "DELETE  FROM `users_table` WHERE `user_name` = ?;";
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            removeUserStat = conn.prepareStatement(removeuser);
            removeUserStat.setString(1, username.toUpperCase());
            int rs = removeUserStat.executeUpdate();
            if (rs > 0) {
                return true;//valid username

            }
            removeUserStat.clearParameters();
            removeUserStat.close();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;//not valid
    }

    /**
     * update user password in the database
     *
     * @param username
     * @param newpassword
     * @return test boolean successful process
     */
    public  boolean updateUserPassword(String username, String newpassword) {

        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }

            String updateProtDesc = "UPDATE  `" + dbName + "`.`users_table` SET `password`= ? WHERE `user_name` = ? ;";
            PreparedStatement updateProtDescStat = conn.prepareStatement(updateProtDesc);
            updateProtDescStat.setString(1, newpassword);
            updateProtDescStat.setString(2, username.toUpperCase());
            int test = updateProtDescStat.executeUpdate();
            updateProtDescStat.clearParameters();
            updateProtDescStat.close();
            if (test > 0) {
                return true;
            }
        } catch (ClassNotFoundException e2) {
            System.err.println(e2.getLocalizedMessage());
//            e2.printStackTrace();
        } catch (IllegalAccessException e2) {
            System.err.println(e2.getLocalizedMessage());
//            e2.printStackTrace();
        } catch (InstantiationException e2) {
            System.err.println(e2.getLocalizedMessage());
//            e2.printStackTrace();
        } catch (SQLException e2) {
            System.err.println(e2.getLocalizedMessage());
//            e2.printStackTrace();
        }

        return false;
    }
    
    /**
     * get peptides data for a database using peptides ids
     *
     * @param peptideIds
     * @return list of peptides
     */
    public Map<Integer, PeptideBean> getPeptidesList(List<Integer> peptideIds) {

        Map<Integer, PeptideBean> peptidesList = new HashMap<Integer, PeptideBean>();
        try {

            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }

            PreparedStatement selectPeptidesStat ;
            String selectPeptide = "SELECT * FROM `proteins_peptides_table` WHERE  `peptide_id`=? ;";

            for (int pepId : peptideIds) {

                PeptideBean pepb = new PeptideBean();
                pepb.setPeptideId(pepId);
                if (conn == null || conn.isClosed()) {
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                }
                selectPeptidesStat = conn.prepareStatement(selectPeptide);
                selectPeptidesStat.setInt(1, pepId);
                ResultSet rs = selectPeptidesStat.executeQuery();

                while (rs.next()) {
                    pepb.setAaAfter(rs.getString("aa_after"));
                    pepb.setAaBefore(rs.getString("aa_before"));
                    pepb.setConfidence(rs.getDouble("confidence"));
                    pepb.setLocationConfidence(rs.getString("location_confidence"));
                    pepb.setNumberOfValidatedSpectra(rs.getInt("number_of_validated_spectra"));
                    pepb.setOtherProteinDescriptions(rs.getString("other_protein_description(s)"));
                    pepb.setOtherProteins(rs.getString("other_protein(s)"));
                    pepb.setPeptideEnd(rs.getString("peptide_end"));
                    pepb.setPeptideProteins((rs.getString("peptide_protein(s)")));
                    pepb.setPeptideProteinsDescriptions(rs.getString("peptide_proteins_description(s)"));
                    pepb.setPeptideStart(rs.getString("peptide_start"));
                    pepb.setPrecursorCharges(rs.getString("precursor_charge(s)"));
                    pepb.setMainProteinAccession(rs.getString("protein"));
                    pepb.setScore(rs.getDouble("score"));
                    pepb.setSequence(rs.getString("sequence"));
                    pepb.setFixedModification(rs.getString("fixed_modification"));
                    pepb.setVariableModification(rs.getString("variable_modification"));
                    pepb.setProteinInference(rs.getString("protein_inference"));
                    pepb.setSequenceTagged(rs.getString("sequence_tagged"));
                    pepb.setEnzymatic(Boolean.valueOf(rs.getString("enzymatic")));
                    pepb.setValidated(rs.getDouble("validated"));
                    pepb.setPeptideId(pepId);
                    pepb.setGlycopatternPositions(rs.getString("glycopattern_position(s)"));
                    String str = rs.getString("deamidation_and_glycopattern");
                    if (str != null && !str.equals("")) {
                        pepb.setDeamidationAndGlycopattern(Boolean.valueOf(str));
                    }
                    pepb.setLikelyNotGlycosite(Boolean.valueOf(rs.getString("likelyNotGlycosite")));

                    if (pepb.getDecoy() != 1) {
                        peptidesList.put(pepb.getPeptideId(), pepb);
                    }
                }
                rs.close();
            }

        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        System.gc();
        return peptidesList;
    }



    /**
     * get peptides id list for selected protein in selected dataset
     *
     * @param datasetId
     * @param accession
     * @return peptides id list for the selected protein group in the selected
     * dataset
     */
    public Set<Integer> getDatasetProteinsPeptidesIds(int datasetId, String accession) {
        PreparedStatement selectDatasetProPepStat ;
        String selectDatasetProPep = "SELECT `peptide_id` FROM `experiment_peptides_proteins_table` WHERE `exp_id`=? AND `protein` LIKE(?)";
        Set<Integer> datasetProPepIds = new HashSet<Integer>();
        try {
            if (conn != null && !conn.isClosed()) {
            } else {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            selectDatasetProPepStat = conn.prepareStatement(selectDatasetProPep);
            selectDatasetProPepStat.setInt(1, datasetId);
            selectDatasetProPepStat.setString(2, "%" + accession.toUpperCase() + "%");
            ResultSet rs = selectDatasetProPepStat.executeQuery();
            while (rs.next()) {
                datasetProPepIds.add(rs.getInt("peptide_id"));
            }
            rs.close();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
        System.gc();
        return datasetProPepIds;
    }
    /**
     * retrieve standard proteins data for fraction plot
     *
     * @param datasetId
     * @return standardPlotList
     */
    public List<StandardProteinBean> getStandardProtPlotList(int datasetId) {
        List<StandardProteinBean> standardPlotList = new ArrayList<StandardProteinBean>();
        try {

            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            String selectPeptide = "SELECT * FROM `standard_plot_proteins` where `exp_id` = ?;";
            PreparedStatement selectPeptideStat = conn.prepareStatement(selectPeptide);
            selectPeptideStat.setInt(1, datasetId);
            ResultSet rs = selectPeptideStat.executeQuery();
            while (rs.next()) {
                StandardProteinBean spb = new StandardProteinBean();
                spb.setMW_kDa(rs.getDouble("mw_(kDa)"));
                spb.setLowerFraction(rs.getInt("lower"));
                spb.setUpperFraction(rs.getInt("upper"));
                spb.setName(rs.getString("name"));
                spb.setColor(rs.getString("color"));
                standardPlotList.add(spb);
            }
            rs.close();
        } catch (ClassNotFoundException exp) {
            System.err.println(exp.getLocalizedMessage());
            return null;
        } catch (IllegalAccessException exp) {
            System.err.println(exp.getLocalizedMessage());
            return null;
        } catch (InstantiationException exp) {
            System.err.println(exp.getLocalizedMessage());
            return null;
        } catch (SQLException exp) {
            System.err.println(exp.getLocalizedMessage());
            return null;
        }
        System.gc();
        return standardPlotList;
    }

    /**
     * retrieve standard proteins data for fraction plot
     *
     * @param dataset
     * @return test boolean
     */
    @SuppressWarnings({"BroadCatchBlock", "TooBroadCatch"})
    public boolean updateDatasetData(DatasetBean dataset) {

        String updateExp = "UPDATE  `" + dbName + "`.`experiments_table`  SET `name`=?,`ready`=? ,`uploaded_by`=?,`species`=?,`sample_type`=?,`sample_processing`=?,`instrument_type`=?,`frag_mode` =?,`proteins_number` = ? ,	`email` =?,`pblication_link`=?,`description`=?,`peptides_number` =?  WHERE `exp_id` = ? ;";
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            PreparedStatement updateExpStat = conn.prepareStatement(updateExp, Statement.RETURN_GENERATED_KEYS);
            updateExpStat.setString(1, dataset.getName().toUpperCase());
            updateExpStat.setInt(2, 2);
            updateExpStat.setString(3, dataset.getUploadedByName().toUpperCase());
            updateExpStat.setString(4, dataset.getSpecies());
            updateExpStat.setString(5, dataset.getSampleType());
            updateExpStat.setString(6, dataset.getSampleProcessing());
            updateExpStat.setString(7, dataset.getInstrumentType());
            updateExpStat.setString(8, dataset.getFragMode());
            updateExpStat.setInt(9, dataset.getProteinsNumber());
            updateExpStat.setString(10, dataset.getEmail().toUpperCase());
            if (dataset.getPublicationLink() != null) {
                updateExpStat.setString(11, dataset.getPublicationLink());
            } else {
                updateExpStat.setString(11, "NOT AVAILABLE");
            }
            updateExpStat.setString(12, dataset.getDescription());
            updateExpStat.setInt(13, dataset.getPeptidesNumber());
            updateExpStat.setInt(14, dataset.getDatasetId());
            int test = updateExpStat.executeUpdate();
            System.gc();
            if (test > 0) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        }

        return false;
    }

    /**
     * store glycopeptides data for dataset in the database
     *
     *
     * @param peptideBean
     * @return test boolean successful process
     */
    private boolean updateGlycoPeptid(PeptideBean peptideBean) {

        String updatePeptide = "UPDATE  `" + dbName + "`.`proteins_peptides_table`  SET  `glycopattern_position(s)` = ?, `deamidation_and_glycopattern` = ?  WHERE  peptide_id=?;";
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            PreparedStatement updatePePStat = conn.prepareStatement(updatePeptide, Statement.RETURN_GENERATED_KEYS);
            updatePePStat.setString(1, peptideBean.getGlycopatternPositions());
            updatePePStat.setString(2, peptideBean.getDeamidationAndGlycopattern().toString());
            updatePePStat.setInt(3, peptideBean.getPeptideId());

            int test = updatePePStat.executeUpdate();
            if (test > 0) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (IllegalAccessException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (InstantiationException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        }

        return false;

    }

    /**
     * store peptides data for dataset in the database
     *
     *
     * @param peptidesList
     * @return test boolean successful process
     */
    @SuppressWarnings("SleepWhileInLoop")
    public  boolean setPeptideFile(Map<Integer, PeptideBean> peptidesList) {
        int counter = 0;
        boolean test = false;
        try {
            for (PeptideBean pb : peptidesList.values()) {

                test = this.updateGlycoPeptid(pb);
                counter++;

                if (counter == 10000) {
                    conn.close();
                    Thread.sleep(100);
                    Class.forName(driver).newInstance();
                    conn = DriverManager.getConnection(url + dbName, userName, password);
                    counter = 0;
                }
            }
        } catch (ClassNotFoundException exp) {
        } catch (IllegalAccessException exp) {
        } catch (InstantiationException exp) {
        } catch (InterruptedException exp) {
        } catch (SQLException exp) {
        }
        return test;
    }

    /**
     * Fill identification protein information from the result set
     *
     * @param resultSet results set to fill identification peptides data
     * @return datasetProteinsList
     *
     */
    private Map<Integer, Map<Integer, ProteinBean>> fillProteinInformation(ResultSet rs, Set<String> searchSet) {
        Map<Integer, Map<Integer, ProteinBean>> proteinsList = new HashMap<Integer, Map<Integer, ProteinBean>>();
        try {
            while (rs.next()) {
                ProteinBean temPb = new ProteinBean();
                temPb.setDatasetId(rs.getInt("exp_id"));
                temPb.setAccession(rs.getString("prot_accession"));
                temPb.setDescription(rs.getString("description"));
                temPb.setOtherProteins(rs.getString("other_protein(s)"));
                temPb.setProteinInference(rs.getString("protein_inference_class"));
                temPb.setSequenceCoverage(rs.getDouble("sequence_coverage(%)"));
                temPb.setObservableCoverage(rs.getDouble("observable_coverage(%)"));
                temPb.setConfidentPtmSites(rs.getString("confident_ptm_sites"));
               
                temPb.setNumberValidatedPeptides(rs.getInt("number_validated_peptides"));
                temPb.setNumberValidatedSpectra(rs.getInt("number_validated_spectra"));
                temPb.setNsaf(rs.getDouble("nsaf"));
                temPb.setMw_kDa(rs.getDouble("mw_(kDa)"));
                temPb.setScore(rs.getDouble("score"));
                temPb.setConfidence(rs.getDouble("confidence"));
                temPb.setNonEnzymaticPeptides(Boolean.valueOf(rs.getString("non_enzymatic_peptides").toUpperCase()));

                temPb.setSpectrumFractionSpread_lower_range_kDa(rs.getString("spectrum_fraction_spread_lower_range_kDa"));
                temPb.setSpectrumFractionSpread_upper_range_kDa(rs.getString("spectrum_fraction_spread_upper_range_kDa"));
                temPb.setPeptideFractionSpread_lower_range_kDa(rs.getString("peptide_fraction_spread_lower_range_kDa"));
                temPb.setPeptideFractionSpread_upper_range_kDa(rs.getString("peptide_fraction_spread_upper_range_kDa"));

                temPb.setGeneName(rs.getString("gene_name"));
                temPb.setChromosomeNumber(rs.getString("chromosome_number"));
                temPb.setValidated(Boolean.valueOf(rs.getString("valid")));
                temPb.setProtGroupId(rs.getInt("prot_group_id"));
                String[] protKey = rs.getString("prot_key").split(",");
                if (!proteinsList.containsKey(temPb.getDatasetId())) {
                    proteinsList.put(temPb.getDatasetId(), new HashMap<Integer, ProteinBean>());
                }
                proteinsList.get(temPb.getDatasetId()).put(temPb.getProtGroupId(), temPb);
                for (String acc : protKey) {
                    if (searchSet.contains(acc.trim())) {
                        searchSet.remove(acc.trim());
                    }
                }

            }
            rs.close();
        } catch (SQLException sqlExcp) {
            System.err.println("at error line 1996 " + this.getClass().getName() + "   " + sqlExcp.getLocalizedMessage());
        }
        return proteinsList;

    }

    /**
     * Get query details that sent from csf-pr quant
     *
     * @param queryText query text contain idex and query key
     * @return query object that has all requested data
     *
     */
    public Query getSearchQuery(String queryText) {
        Query query = new Query();
        String[] queryDetails = queryText.split("_");
        String statment = "SELECT * FROM `temp_query_table` WHERE `index` = ? ;";

        try {

            if (conn == null || conn.isClosed()) {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            PreparedStatement queryStat = conn.prepareStatement(statment);
            queryStat.setInt(1, Integer.valueOf(queryDetails[1]));
            ResultSet rs = queryStat.executeQuery();
            System.out.println("at " + queryDetails[1] + "  " + queryDetails[2]);
            while (rs.next()) {
                if (queryDetails[2].equalsIgnoreCase(rs.getString("csrf_token"))) {
                    query.setSearchBy(rs.getString("search_by").replace("*", " "));
                    query.setSearchKeyWords(rs.getString("search_key"));
                }

            }
            if (query.getSearchBy() == null) {
                query.setSearchBy("Protein Accession");
                query.setSearchKeyWords("Searching query expired!");
            }
            rs.close();
        } catch (ClassNotFoundException exp) {
            System.out.println(exp.getLocalizedMessage());
        } catch (IllegalAccessException exp) {
            System.out.println(exp.getLocalizedMessage());
        } catch (InstantiationException exp) {
            System.out.println(exp.getLocalizedMessage());
        } catch (SQLException exp) {
            exp.printStackTrace();
            System.out.println(exp.getLocalizedMessage());
        }
        return query;
    }

}
