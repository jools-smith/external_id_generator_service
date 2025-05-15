

package com.flexnet.external.webservice.idgenerator;

import com.flexnet.external.type.BulkEntitlement;
import com.flexnet.external.type.ConsolidatedLicenseRecord;
import com.flexnet.external.type.Entitlement;
import com.flexnet.external.type.EntitlementLineItem;
import com.flexnet.external.type.FulfillmentRecord;
import com.flexnet.external.type.Id;
import com.flexnet.external.type.MaintenanceItem;
import com.flexnet.external.type.PingRequest;
import com.flexnet.external.type.PingResponse;
import com.revenera.gcs.Application;
import com.revenera.gcs.ServiceBase;
import com.revenera.gcs.implementor.PingInfo;
import com.revenera.gcs.utils.*;

import javax.jws.WebService;
import java.time.Instant;

@WebService(
        endpointInterface="com.flexnet.external.webservice.idgenerator.IdGeneratorServiceInterface",
        wsdlLocation = "/schema/IdGeneratorService.wsdl"
)


public class IdGeneratorServiceImpl extends ServiceBase implements IdGeneratorServiceInterface {

  private Id genenerate(final StringGenerator generator) throws IdGeneratorException {
    try {
      return new Id() {
        {
          this.id = generator.build();
        }
      };
    }
    catch(final Throwable t) {
      throw new IdGeneratorException(t.getMessage(), super.serviceException.apply(t));
    }
  }

  private PingResponse ping() throws IdGeneratorException {
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
      throw new IdGeneratorException(t.getMessage(), super.serviceException.apply(t));
    }
  }

  @Override
  public PingResponse ping(final PingRequest payload) throws IdGeneratorException {

    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return ping();
  }

  @Override
  public Id generateEntitlementID(final Entitlement entitlement) throws IdGeneratorException {
    return genenerate(StringGenerator.create()
                                     .withPrefix("ENT")
                                     .withElements("-", Strings.alpha.toUpperCase(), 4, 8));
  }

  @Override
  public Id generateLineItemID(final EntitlementLineItem payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return genenerate(StringGenerator
                              .create()
                              .withPrefix("ACT")
                              .withElement("-", Strings.alpha.toUpperCase(), 4)
                              .withElement("-", Strings.hex.toUpperCase(), 4)
                              .withElement("-", Strings.numeric, 4)
                              .withElement("-", Strings.alpha_numeric.toUpperCase(), 4));
  }

  @Override
  public Id generateWebRegKey(final BulkEntitlement payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return genenerate(StringGenerator
                              .create()
                              .withPrefix("WEB")
                              .withElement("-", Strings.alpha.toUpperCase(), 4)
                              .withElement("-", Strings.hex.toUpperCase(), 4)
                              .withElement("-", Strings.numeric, 4)
                              .withElement("-", Strings.alpha_numeric.toUpperCase(), 4));
  }

  @Override
  public Id generateMaintenanceItemID(final MaintenanceItem payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return genenerate(StringGenerator
                              .create()
                              .withPrefix("WEB")
                              .withCase(Case.Upper)
                              .withElement("-", Strings.alpha, 4)
                              .withElement("-", Strings.hex, 4)
                              .withElement("-", Strings.numeric, 4)
                              .withElement("-", Strings.alpha_numeric, 4));
  }

  @Override
  public Id generateFulfillmentID(final FulfillmentRecord payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return genenerate(StringGenerator
                              .create()
                              .withPrefix("FID")
                              .withSeparator(Strings.HYPHEN)
                              .withCase(Case.Lower)
                              .withGuid());
  }

  @Override
  public Id generateConsolidatedLicenseID(final ConsolidatedLicenseRecord payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return genenerate(StringGenerator.create()
                                     .withPrefix("CID")
                                     .withElement("-", Strings.alpha.toUpperCase(), 4)
                                     .withElement("-", Strings.hex.toUpperCase(), 4)
                                     .withElement("-", Strings.numeric, 4)
                                     .withCase(Case.Upper)
                                     .withElement("-", Strings.alpha_numeric.toUpperCase(), 4));
  }
}
