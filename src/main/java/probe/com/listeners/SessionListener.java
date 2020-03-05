package probe.com.listeners;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session listener to set time out and clean after session expires
 *
 * @author Yehia Farag
 */
public class SessionListener implements HttpSessionListener, Serializable {

    private Timer timer;
    private HttpSession session;

    /**
     * On start new session create timer to set expiring time
     *
     * @param hse start session event
     */
    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        session = hse.getSession();
        if (session != null) {
            timer = new Timer();
            timer.schedule(new RemindTask(), (5 * 60 * 60 * 1000));

        }
    }

    /**
     * On the end of session
     *
     * @param hse
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {

    }

    /**
     * This class is represents time tracker for session
     */
    class RemindTask extends TimerTask {

        @Override
        public void run() {
            session.invalidate();
            timer.cancel(); //Not necessary because we call System.exit
        }
    };

}
