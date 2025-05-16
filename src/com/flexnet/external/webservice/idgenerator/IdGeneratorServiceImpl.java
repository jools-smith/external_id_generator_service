

package com.flexnet.external.webservice.idgenerator;

import com.flexnet.external.type.*;
import com.flexnet.external.webservice.ServiceBase;
import com.revenera.gcs.random.Case;
import com.revenera.gcs.random.StringGenerator;
import com.revenera.gcs.random.Charset;
import com.revenera.gcs.utils.Log;

import javax.jws.WebService;

@WebService(
        endpointInterface="com.flexnet.external.webservice.idgenerator.IdGeneratorServiceInterface",
        wsdlLocation = "/schema/IdGeneratorService.wsdl"
)
public class IdGeneratorServiceImpl extends ServiceBase implements IdGeneratorServiceInterface {

  @Override
  public PingResponse ping(final PingRequest payload) throws IdGeneratorException {

    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return createPingResponse();
  }

  @Override
  public Id generateEntitlementID(final Entitlement entitlement) throws IdGeneratorException {
    return createId(StringGenerator.create()
                                   .withPrefix("ENT")
                                   .withCase(Case.Upper)
                                   .withElements(Charset.HYPHEN, Charset.alpha_numeric_safe, 4, 8));
  }

  @Override
  public Id generateLineItemID(final EntitlementLineItem payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return createId(StringGenerator
                              .create()
                              .withPrefix("ACT")
                              .withCase(Case.Upper)
                              .withElements(Charset.HYPHEN, Charset.hex, 4, 8));
  }

  @Override
  public Id generateWebRegKey(final BulkEntitlement payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return createId(StringGenerator
                              .create()
                              .withPrefix("WEB")
                              .withCase(Case.Upper)
                              .withElements(Charset.HYPHEN, Charset.hex, 6, 6));
  }

  @Override
  public Id generateMaintenanceItemID(final MaintenanceItem payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return createId(StringGenerator
                              .create()
                              .withPrefix("MNT")
                              .withCase(Case.Upper)
                              .withElements(Charset.HYPHEN, Charset.hex, 4, 8));
  }

  @Override
  public Id generateFulfillmentID(final FulfillmentRecord payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return createId(StringGenerator
                              .create()
                              .withPrefix("FID")
                              .withSeparator(Charset.UNDERSCORE)
                              .withCase(Case.Lower)
                              .withGuid());
  }

  @Override
  public Id generateConsolidatedLicenseID(final ConsolidatedLicenseRecord payload) throws IdGeneratorException {
    super.logger.in();

    super.logger.yaml(Log.Level.trace, payload);

    return createId(StringGenerator
                            .create()
                            .withPrefix("CID")
                            .withSeparator(Charset.UNDERSCORE)
                            .withCase(Case.Lower)
                            .withGuid());
  }
}
