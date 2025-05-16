import com.flexnet.external.type.PingRequest;
import com.flexnet.external.type.PingResponse;
import com.flexnet.external.webservice.idgenerator.IdGeneratorServiceImpl;
import com.revenera.gcs.Application;
import com.revenera.gcs.random.Case;
import com.revenera.gcs.random.Charset;
import com.revenera.gcs.random.StringGenerator;
import com.revenera.gcs.utils.Log;

public class Test {
  final static Log logger = Log.create(Test.class);

  public static void main(final String... args) {
    logger.in();

    try {


      System.out.println(StringGenerator.create()
                                        .withPrefix("ABC")
                                        .withSeparator(Charset.HYPHEN)
                                        .withCase(Case.Upper)
                                        .withElement(Charset.hex, 8)
                                        .withSeparator(Charset.HYPHEN)
                                        .withElement(Charset.alpha_numeric, 8)
                                        .withSeparator(Charset.HYPHEN)
                                        .withElement(Charset.numeric, 8)
                                        .withSeparator(Charset.HYPHEN)
                                        .withElement(Charset.alpha_numeric_safe, 8)
                                        .build());

      final Application app  = new Application();

      final IdGeneratorServiceImpl service = new IdGeneratorServiceImpl();

      final PingResponse ping = service.ping(new PingRequest() {
        {
          this.str = "hello world...";
        }
      });

     logger.yaml(Log.Level.info, ping);
    }
    catch (final Throwable t) {
      logger.exception(t);
    }
    finally {
      logger.out();
    }
  }
}
