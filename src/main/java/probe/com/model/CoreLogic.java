package probe.com.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import probe.com.dal.DataBase;
import probe.com.dal.Query;
import probe.com.model.beans.DatasetBean;
import probe.com.model.beans.FractionBean;
import probe.com.model.beans.PeptideBean;
import probe.com.model.beans.ProteinBean;
import probe.com.model.beans.StandardProteinBean;

/**
 * This class represents the core logic layer
 *
 * @author Yehia Farag
 */
public class CoreLogic implements Serializable {

    private static final long serialVersionUID = 1L;
    private final DataBase database;
    private int mainDatasetId;
    private final TreeMap<Integer, String> datasetNamesList = new TreeMap<Integer, String>();//for dropdown select list
    private Map<Integer, DatasetBean> datasetList;
    private final Map<Integer, Integer> datasetIndex = new HashMap<Integer, Integer>();

    /**
     * Initialise main logic layer
     *
     * @param url database url
     * @param dbName database name
     * @param driver database driver
     * @param userName database username
     * @param password database password
     */
    public CoreLogic(String url, String dbName, String driver, String userName, String password) {
        database = new DataBase(url, dbName, driver, userName, password);

        /**
         * @todo:just to arrange the already stored datasets (to be removed in
         * the future).
         */
        datasetIndex.put(8, 1);
        datasetIndex.put(14, 2);
        datasetIndex.put(4, 3);
        datasetIndex.put(17, 4);
        datasetIndex.put(15, 5);
        datasetIndex.put(16, 6);
        datasetIndex.put(9, 7);
        datasetList = database.getDatasets();
    }

    /**
     * get the datasets names required for initialising drop down select list
     *
     * @return datasetNamesList
     */
    public TreeMap<Integer, String> getDatasetNamesList() {
        if (datasetList == null) {
            datasetList = getDatasetList();
            if (datasetList == null) {
                return new TreeMap<Integer, String>();
            }

        }
        for (int datasetkey : datasetList.keySet()) {
            //for re-indexing the stored datasets, to be removed in the future
            if (datasetIndex.containsKey(datasetkey)) {
                DatasetBean dataset = datasetList.get(datasetkey);
                datasetNamesList.put(datasetIndex.get(datasetkey), "\t" + dataset.getName());

            } else {
                DatasetBean dataset = datasetList.get(datasetkey);
                datasetNamesList.put(datasetkey, "\t" + dataset.getName());
                datasetIndex.put(datasetkey, datasetkey);
            }
        }
        return datasetNamesList;

    }

    /**
     * get the available datasets
     *
     * @return datasetsList
     */
    public Map<Integer, DatasetBean> getDatasetList() {
        if (datasetList == null || datasetList.isEmpty()) {
            datasetList = database.getDatasets();
        }
        return datasetList;
    }

    /**
     * get selected dataset
     *
     * @param datasetId
     * @return dataset
     */
    public DatasetBean getDataset(int datasetId) {
        DatasetBean dataset = datasetList.get(datasetId);
        if (dataset == null) {
            dataset = database.getStoredDataset(datasetId);
            datasetList.put(datasetId, dataset);
        }
        return dataset;
    }

    /**
     * get proteins map for especial dataset
     *
     * @param datasetId
     * @return proteinsList
     */
    public Map<String, ProteinBean> retriveProteinsList(int datasetId) {
        Map<String, ProteinBean> proteinsList;
        if (datasetList.get(datasetId).getProteinList() == null || datasetList.get(datasetId).getProteinList().isEmpty()) {
            proteinsList = database.getDatasetProteinsList(datasetId);
            datasetList.get(datasetId).setProteinList(proteinsList);

        } else {
            proteinsList = datasetList.get(datasetId).getProteinList();
        }

        return proteinsList;
    }

    /**
     * get dataset peptides list
     *
     * @param datasetId
     * @return dataset peptide List
     */
    public Map<Integer, PeptideBean> getPeptidesList(int datasetId) {
        Map<Integer, PeptideBean> updatedPeptidesList = new HashMap<Integer, PeptideBean>();
        Map<Integer, PeptideBean> peptidesList = database.getDatasetPeptidesList(datasetId);
        Map<String, ProteinBean> protList = retriveProteinsList(datasetId);

        for (int key : peptidesList.keySet()) {
            PeptideBean pb = peptidesList.get(key);
            if (pb.getProteinInference().equalsIgnoreCase("Single Protein")) {
                pb.setPeptideProteins(pb.getMainProteinAccession());
                pb.setPeptideProteinsDescriptions(datasetList.get(datasetId).getProteinList().get(pb.getMainProteinAccession()).getDescription());
            } else if (pb.getProteinInference().trim().equalsIgnoreCase("Related Proteins") && (!pb.getMainProteinAccession().equalsIgnoreCase("SHARED PEPTIDE"))) {
                String desc;
                if (pb.getOtherProteins() == null || pb.getOtherProteins().trim().equalsIgnoreCase("")) {
                    pb.setPeptideProteins(pb.getMainProteinAccession() + "," + pb.getPeptideProteins());
                    desc = protList.get(pb.getMainProteinAccession()).getDescription() + ";" + pb.getPeptideProteinsDescriptions();
                } else {
                    desc = protList.get(pb.getMainProteinAccession() + "," + pb.getOtherProteins().replaceAll("\\p{Z}", "")).getDescription() + ";" + pb.getOtherProteinDescriptions();
                }
                pb.setPeptideProteinsDescriptions(desc);
            }
            updatedPeptidesList.put(key, pb);
        }
        return updatedPeptidesList;
    }

    /**
     * get dataset peptides list (valid peptides or all peptides)
     *
     * @param datasetId
     * @param validated validated peptides (true/false)
     * @return dataset peptide List
     */
    public Map<Integer, PeptideBean> getAllDatasetPeptidesList(int datasetId, boolean validated) {

        Map<Integer, PeptideBean> peptidesList = getPeptidesList(datasetId);
        if (validated) {
            Map<Integer, PeptideBean> validatedPtidesList = new HashMap<Integer, PeptideBean>();
            int x = 0;
            for (PeptideBean pb : peptidesList.values()) {

                if (pb.getValidated() == 1.0) {
                    validatedPtidesList.put(x++, pb);
                }
            }
            return validatedPtidesList;
        } else {
            return peptidesList;
        }
    }

    /**
     * get dataset peptides number (valid peptides or all peptides)
     *
     * @param datasetId
     * @param validated validated peptides (true/false)
     * @return dataset peptide List
     */
    public int getAllDatasetPeptidesNumber(int datasetId, boolean validated) {

        Map<Integer, PeptideBean> peptidesList = database.getDatasetPeptidesList(datasetId);
        int index = 0;
        if (validated) {
            int x = 0;
            for (PeptideBean pb : peptidesList.values()) {

                if (pb.getValidated() == 1.0) {
                    index++;
                }
            }
            return index;
        } else {
            return peptidesList.size();
        }
    }

    /**
     * get dataset fractions list
     *
     * @param datasetId
     * @return fractions list for the selected dataset
     */
    public Map<Integer, FractionBean> getFractionsList(int datasetId) {
        Map<Integer, FractionBean> fractionsList;
        if (datasetList.containsKey(datasetId) && datasetList.get(datasetId).getFractionsList() != null && (!datasetList.get(datasetId).getFractionsList().isEmpty())) {
            //check if dataset updated if not
            fractionsList = datasetList.get(datasetId).getFractionsList();

        } else {
            fractionsList = database.getFractionsList(datasetId);
            datasetList.get(datasetId).setFractionsList(fractionsList);
        }

        return fractionsList;
    }

    ///new v-2
    /**
     * search for proteins by accession keywords
     *
     * @param searchArr array of query words
     * @param searchDatasetType type of search
     * @param validatedOnly only validated proteins results
     * @return datasetProtList
     */
    public Map<Integer, ProteinBean> searchProteinByAccession(Set<String> searchArr, String searchDatasetType, boolean validatedOnly) {
        Map<Integer, ProteinBean> datasetProtList = new HashMap<Integer, ProteinBean>();
        Map<Integer, Map<Integer, ProteinBean>> protTempList = searchProteinByAccession(searchArr, validatedOnly);
        for (DatasetBean tempDataset : datasetList.values()) {
            if (tempDataset.getName().equalsIgnoreCase(searchDatasetType)) {
                return protTempList.get(tempDataset.getDatasetId());
            }
        }
        for (Map<Integer, ProteinBean> list : protTempList.values()) {
            datasetProtList.putAll(list);
        }
        return datasetProtList;

    }

    /**
     * search for proteins by accession keywords
     *
     * @param accession array of query words
     * @param datasetId
     * @param validatedOnly only validated proteins results
     * @return datasetProtList
     */
    private Map<Integer, Map<Integer, ProteinBean>> searchProteinByAccession(Set<String> accession, boolean validatedOnly) {
        Map<Integer, Map<Integer, ProteinBean>> protDatasetpList = database.searchIdentificationProteinAllDatasetsByAccession(accession, validatedOnly);
        return protDatasetpList;
    }

    /**
     * get peptides list for selected protein in selected dataset
     *
     * @param datasetId
     * @param accession
     * @param otherAccession
     * @return peptides list for the selected protein group in the selected
     * dataset
     */
    public Map<Integer, PeptideBean> getPeptidesProtList(int datasetId, String accession, String otherAccession) {
        Set<Integer> peptideIds = this.getDatasetProteinPeptidesIds(datasetId, accession, otherAccession);
        Map<Integer, PeptideBean> peptidesProtList = database.getPeptidesList(peptideIds);
        datasetList.get(datasetId).setPeptidesIds(peptideIds);
        return peptidesProtList;
    }

    /**
     * search for proteins by description keywords
     *
     * @param proteinDescriptionKeyword array of query words
     * @param searchDatasetType type of search
     * @param validatedOnly only validated proteins results
     * @return datasetProteinsSearchList
     */
    public Map<Integer, ProteinBean> searchProteinByName(String proteinDescriptionKeyword, String searchDatasetType, boolean validatedOnly) {

        Map<Integer, ProteinBean> datasetProteinsSearchList = new HashMap<Integer, ProteinBean>();

        DatasetBean dataset = null;
        if (!searchDatasetType.equalsIgnoreCase("Search All Datasets")) {
            for (DatasetBean tempDataset : datasetList.values()) {
                if (tempDataset.getName().equalsIgnoreCase(searchDatasetType)) {
                    dataset = tempDataset;
                    break;

                }
            }
            if (dataset != null) {
                datasetProteinsSearchList = searchProteinByName(proteinDescriptionKeyword, dataset.getDatasetId(), validatedOnly);
            }

        } else {
            for (DatasetBean tempDataset : datasetList.values()) {
                datasetProteinsSearchList.putAll(searchProteinByName(proteinDescriptionKeyword, tempDataset.getDatasetId(), validatedOnly));

            }
        }

        return datasetProteinsSearchList;

    }

    /**
     * search for proteins by protein description keywords
     *
     * @param protSearchKeyword array of query words
     * @param datasetId dataset Id
     * @param validatedOnly only validated proteins results
     * @return datasetProteinsSearchList
     */
    private Map<Integer, ProteinBean> searchProteinByName(String protSearchKeyword, int datasetId, boolean validatedOnly) {
        Map<Integer, ProteinBean> proteinsList = database.searchProteinByName(protSearchKeyword, datasetId, validatedOnly);
        return proteinsList;
    }

    /**
     * search for proteins by peptide sequence keywords
     *
     * @param peptideSequenceKeyword array of query words
     * @param searchDatasetType type of search
     * @param validatedOnly only validated proteins results
     * @return datasetProteinsSearchList
     */
    public Map<Integer, ProteinBean> searchProteinByPeptideSequence(String peptideSequenceKeyword, String searchDatasetType, boolean validatedOnly) {
        Map<Integer, ProteinBean> protDatasetList = new HashMap<Integer, ProteinBean>();
        DatasetBean dataset = null;
        if (!searchDatasetType.equalsIgnoreCase("Search All Datasets")) {
            for (DatasetBean tempDataset : datasetList.values()) {
                if (tempDataset.getName().equalsIgnoreCase(searchDatasetType)) {
                    dataset = tempDataset;
                    break;

                }
            }
            if (dataset != null) {
                protDatasetList = searchProteinByPeptideSequence(peptideSequenceKeyword, dataset.getDatasetId(), validatedOnly);
            }

        } else {
            for (DatasetBean tempDataset : datasetList.values()) {
                Map<Integer, ProteinBean> protTempList = searchProteinByPeptideSequence(peptideSequenceKeyword, tempDataset.getDatasetId(), validatedOnly);
                if (protTempList != null && (!protTempList.isEmpty())) {
                    protDatasetList.putAll(protTempList);
                }

            }
        }
        return protDatasetList;

    }

    /**
     * search for proteins by peptide sequence keywords
     *
     * @param peptideSequenceKeyword array of query words
     * @param datasetId dataset Id
     * @param validatedOnly only validated proteins results
     * @return datasetProteinsSearchList
     */
    public Map<Integer, ProteinBean> searchProteinByPeptideSequence(String peptideSequenceKeyword,
            int datasetId, boolean validatedOnly) {
        Map<Integer, ProteinBean> proteinsList = database.searchProteinByPeptideSequence(peptideSequenceKeyword, datasetId, validatedOnly);
        return proteinsList;
    }

    /**
     * get proteins fractions average list
     *
     * @param accession
     * @param datasetId
     * @return dataset peptide List
     */
    public Map<Integer, FractionBean> getProteinFractionList(String accession,
            int datasetId) {
        Map<Integer, FractionBean> protionFractList = database.getProteinFractionList(accession, datasetId);
        return protionFractList;
    }

    /**
     * get peptides data for a database using peptides ids
     *
     * @param peptideIds
     * @return list of peptides
     */
    public Map<Integer, PeptideBean> getPeptidesList(List<Integer> peptideIds) {
        Map<Integer, PeptideBean> peptidesProtList = database.getPeptidesList(peptideIds);
        return peptidesProtList;
    }

    /**
     * get peptides id list for selected protein in selected dataset
     *
     * @param datasetId
     * @param accession
     * @param otherAccession
     * @return peptides id list for the selected protein group in the selected
     * dataset
     */
    private Set<Integer> getDatasetProteinPeptidesIds(int datasetId, String accession, String otherAccession) {
        Set<Integer> datasetProteinPeptidesIds = database.getDatasetProteinsPeptidesIds(datasetId, accession);
        if (otherAccession != null && !otherAccession.equals("")) {
            String[] otherAccessionArr = otherAccession.split(",");
            for (String str : otherAccessionArr) {
                datasetProteinPeptidesIds.addAll(database.getDatasetProteinsPeptidesIds(datasetId, str.trim()));
            }
        }

        return datasetProteinPeptidesIds;
    }

    /**
     * retrieve standard proteins data for fraction plot
     */
    public void retriveStandardProtPlotList() {
        List<StandardProteinBean> standardPlotList = database.getStandardProtPlotList(mainDatasetId);
        getMainDataset().setStanderdPlotProt(standardPlotList);
    }

    /**
     * retrieve standard proteins data for fraction plot
     *
     * @param datasetId
     */
    public void retriveStandardProtPlotList(int datasetId) {
        List<StandardProteinBean> standardPlotList = database.getStandardProtPlotList(datasetId);
        getMainDataset().setStanderdPlotProt(standardPlotList);
    }

    /**
     * retrieve standard proteins data for fraction plot
     *
     * @param dataset
     * @return test boolean
     */
    public boolean updateDatasetData(DatasetBean dataset) {
        boolean test = database.updateDatasetData(dataset);
        return test;

    }

    /**
     * get datasetIndex List to be removed in the future this function to
     * re-arrange the already stored datasets in the database and return the
     * dataset id
     *
     * @return datasetIndexList
     */
    public Map<Integer, Integer> getDatasetIndexList() {
        return datasetIndex;
    }

    /**
     * get the main dataset
     *
     * @return mainDataset
     */
    public DatasetBean getMainDataset() {
        return datasetList.get(mainDatasetId);
    }

    /**
     * Get the dataset key
     *
     * @param datasetString
     * @return integer dataset unique key
     */
    public int getDatasetKey(String datasetString) {
        for (int key1 : datasetNamesList.keySet()) {
            if (datasetString.equalsIgnoreCase(datasetNamesList.get(key1))) {
                for (int k : datasetIndex.keySet()) {
                    int value = datasetIndex.get(k);
                    if (value == key1) {
                        return k;
                    }
                }
                return datasetIndex.get(key1);
            }
        }
        return 0;
    }

    /**
     * set the main dataset selected by user
     *
     * @param datasetString string from drop down select list
     * @return datasetId
     */
    public int setMainDataset(String datasetString) {
        for (int tempDatasetIndex : datasetNamesList.keySet()) {
            if (datasetString.trim().equalsIgnoreCase(datasetNamesList.get(tempDatasetIndex).trim())) {

                for (int k : datasetIndex.keySet()) {
                    int value = datasetIndex.get(k);
                    if (value == tempDatasetIndex) {
                        setMainDataset(k);
                        return k;
                    }
                }
                setMainDataset(tempDatasetIndex);
                return tempDatasetIndex;
            }
        }
        return 0;
    }

    /**
     * set the selected dataset as main dataset in the logic layer
     *
     * @param datasetId
     */
    public void setMainDataset(int datasetId) {
        this.mainDatasetId = datasetId;
    }

    /**
     * get dataset details list that has basic information for datasets
     *
     * @return datasetDetailsList
     */
    public Map<Integer, DatasetBean> getDatasetDetailsList() {
        Map<Integer, DatasetBean> datasetDetailsList = new HashMap<Integer, DatasetBean>();
        for (DatasetBean dataset : datasetList.values()) {
            DatasetBean datasetDetails = new DatasetBean();
            datasetDetails.setName(dataset.getName());
            datasetDetails.setFragMode(dataset.getFragMode());
            datasetDetails.setInstrumentType(dataset.getInstrumentType());
            datasetDetails.setSampleType(dataset.getSampleType());
            datasetDetails.setSampleProcessing(dataset.getSampleProcessing());
            datasetDetails.setSpecies(dataset.getSpecies());
            datasetDetailsList.put(dataset.getDatasetId(), datasetDetails);

        }
        return datasetDetailsList;

    }

    /**
     * get number of validated peptides for protein
     *
     * @param pepProtList
     *
     * @return number of valid peptides
     */
    public int getValidatedPepNumber(Map<Integer, PeptideBean> pepProtList) {
        int count = 0;
        for (PeptideBean pb : pepProtList.values()) {
            if (pb.getValidated() == 1.0) {
                count++;
            }
        }
        return count;
    }

    /**
     * calculate the map of search indexes
     *
     * @param searchMap result map
     * @param searchMapIndex map of the indexes
     * @param keySearch keyword used for searching
     * @return sorted indexed map
     */
    public TreeMap<Integer, Integer> getSearchIndexesSet(Map<String, Integer> searchMap, Map<String, Integer> searchMapIndex, String keySearch) {
        TreeMap<Integer, Integer> treeSet = new TreeMap<Integer, Integer>();
        for (String key : searchMap.keySet()) {
            if (key.contains(keySearch)) {
                treeSet.put(searchMapIndex.get(key), searchMap.get(key));
            }
        }

        return treeSet;
    }

    /**
     * get validated proteins list
     *
     * @param proteinsList
     *
     * @return vProteinsList list of valid proteins
     */
    public Map<Integer, ProteinBean> getValidatedProteinsList(Map<Integer, ProteinBean> proteinsList) {
        Map<Integer, ProteinBean> vProteinsList = new HashMap<Integer, ProteinBean>();
        for (int str : proteinsList.keySet()) {
            ProteinBean pb = proteinsList.get(str);
            if (pb.isValidated()) {
                vProteinsList.put(str, pb);
            }

        }
        return vProteinsList;

    }

    /**
     * Get query details that sent from csf-pr quant
     *
     * @param queryText query text contain idex and query key
     * @return query object that has all requested data
     *
     */
    public Query getSearchQuery(String queryText) {
        return database.getSearchQuery(queryText);
    }

}
