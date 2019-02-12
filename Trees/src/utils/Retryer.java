package utils;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A retry utility used for executing a function that is unreliable by nature
 */
public class Retryer implements Serializable {
  private static final long serialVersionUID = -6189508476920813L;
  private final Logger log;
  private final String logMessage;
  private final int maxAttempts;
  private final int retryWaitInMillis;

  /**
   * Constructs a {@link Retryer} that takes essentially any function and runs it according to the given
   * maxAttempts and waitInMillis time
   * @param maxAttempts: attempt times
   * @param retryWaitInMillis: wait time (millis) after exception. Increases exponentially on each retry.
   * @param log: nullable, Logging used
   * @param logMessage: nullable, Logging message, passing in empty string if exception message is good enough
   */
  public Retryer(Logger log, String logMessage, int maxAttempts, int retryWaitInMillis) {
    this.log = log;
    this.logMessage = logMessage;
    this.maxAttempts = maxAttempts;
    this.retryWaitInMillis = retryWaitInMillis;
  }

  /**
   * Constructs a {@link Retryer} without attempt logging.
   * See {@link Retryer#Retryer(DCPLogger, String, int, int)}
   */
  public Retryer(int maxAttempts, int retryWaitInMillis) {
    this(null,null, maxAttempts, retryWaitInMillis);
  }

  public <V> V retry(Callable<V> callable) {
    //retry up to maxAttempts+1 times, throw on last try
    for (int attempt = 1;; attempt++) {
      try {
        return callable.call();
      } catch (Exception e) {
        if (log != null) {
          log.log(Level.SEVERE, "Attempt (" + attempt + "/" + maxAttempts + ") failed:" + logMessage + ", message=" + e.getMessage());
        }
        if (attempt < maxAttempts) {
          try {
            Thread.sleep(retryWaitInMillis * (1 << attempt));
          } catch (InterruptedException ie) {
            if (log != null) {
              log.log(Level.WARNING,"Interrupted while waiting for retryWithDefaults.", ie);
            }
          }
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  /**
   * This takes essentially any function and runs it according to the given maxAttempts and waitInMillis time
   * e.g. wrap the function into a lambda expression that conforms to {@link Callable} inferface
   * @return
   */
  public void retry(CheckedRunnable runnable) {
    retry(() -> {
      runnable.run();
      return null;
    });
  }
}


