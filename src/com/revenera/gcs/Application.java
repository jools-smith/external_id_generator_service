package com.revenera.gcs;


import com.revenera.gcs.utils.Log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The root of the service, registered as a listener will set stuff up when the context is initialized
 */
@WebListener
public class Application implements ServletContextListener {
  private static final Log logger = Log.create(Application.class);

  private String web_inf;

  /** instance */
  private static final AtomicReference<Application> singleton = new AtomicReference<>();


  public static Application getInstance() {
    return singleton.get();
  }

  /** incremental build sequence */
  private final String buildSequence;
  private final String buildDate;
  private final String release;

  public String getBuildSequence() {
    return buildSequence;
  }
  public String getBuildDate() {
    return buildDate;
  }
  public String getRelease() {
    return release;
  }

  public String getVersionString() {
    return String.format("%s | %s | %s", getBuildDate(), getBuildSequence(), getRelease());
  }


  private void logAttributeNames(final ServletContextEvent event) {
    final Enumeration<String> itt = event.getServletContext().getAttributeNames();
    while (itt.hasMoreElements()) {
      logger.log(Log.Level.trace, itt.nextElement());
    }
  }

  public Application() {
    /// we can reduce this potentially -- once levels have been assessed
    Log.setLoggingLevel(Log.Level.trace);

    logger.me(this);

    this.buildSequence = "1009";
    this.buildDate = "2025.05.16";
    //TODO: -> GA
    this.release = "BETA";

    singleton.getAndSet(this);

    logger.array(Log.Level.info, "version", getVersionString());
  }


  public Path getResourcePath(final String...parts) {
    return this.web_inf == null ? Paths.get("") : Paths.get(this.web_inf, parts);
  }

  @Override
  public void contextInitialized(final ServletContextEvent event) {
    logger.in();

    try {
      logAttributeNames(event);

      this.web_inf = event.getServletContext().getRealPath("/WEB-INF");

      logger.array(Log.Level.info, "resources", getResourcePath());

      //TODO - add additional start-up code here
    }
    catch (final Throwable t) {
      logger.exception(t);
    }
    finally {
      logger.out();
    }
  }

  @Override
  public void contextDestroyed(final ServletContextEvent event) {
    logger.in();
    try {
      logAttributeNames(event);
    }
    catch (final Throwable t) {
      logger.exception(t);
    }
    finally {
      logger.out();
    }
  }
}
