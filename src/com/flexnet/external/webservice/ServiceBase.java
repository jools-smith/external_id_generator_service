package com.flexnet.external.webservice;

import com.flexnet.external.type.*;
import com.flexnet.external.webservice.idgenerator.IdGeneratorException;
import com.revenera.gcs.Application;
import com.revenera.gcs.random.StringGenerator;
import com.revenera.gcs.utils.Log;
import com.revenera.gcs.utils.Utils;

import java.time.Instant;
import java.util.function.Function;


public abstract class ServiceBase {

  protected final Log logger = Log.create(this.getClass());

  protected ServiceBase() {
    this.logger.in();
  }

  protected PingResponse createPingResponse() throws IdGeneratorException {
    try {
      return new PingResponse() {
        {
          final PingInfo pinfo = PingInfo.create();

          this.info = Utils.safeSerializeYaml(pinfo);

          this.str = String.format("%s | %s | %s | %s | %s | %s | %s | %s",
                                   logger.type().getSimpleName(),
                                   Application.getInstance().getVersionString(),
                                   pinfo.system.name,
                                   pinfo.system.version,
                                   pinfo.system.architecture,
                                   pinfo.hostName,
                                   pinfo.userName,
                                   Application.getInstance().getResourcePath().toString());

          this.processedTime = Instant.now().toString();
        }
      };
    }
    catch(final Throwable t) {
      throw new IdGeneratorException(t.getMessage(), raiseServiceException.apply(t));
    }
  }

  protected Id createId(final StringGenerator generator) throws IdGeneratorException {
    try {
      return new Id() {
        {
          this.id = generator.build();
        }
      };
    }
    catch(final Throwable t) {
      throw new IdGeneratorException(t.getMessage(), raiseServiceException.apply(t));
    }
  }

  public Function<Throwable, com.flexnet.external.type.SvcException> raiseServiceException = (throwable) -> new SvcException() {
    {
      this.setMessage(Utils.frameDetails(Thread.currentThread().getStackTrace()[3]));

      this.setName(throwable.getClass().getName());
    }
  };
}