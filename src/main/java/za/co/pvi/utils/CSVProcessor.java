package za.co.pvi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVProcessor {
    private static final Logger log = LoggerFactory.getLogger(CSVProcessor.class);

    public static  List <Map<String, String>> ProcessFile(MultipartFile multipartFile){

        List <Map<String, String>> list = new ArrayList <>();

        try (InputStream in = multipartFile.getInputStream()) {
            CSV csv = new CSV(true, ',', in );
            List<String> fieldNames = null;
            if (csv.hasNext()) fieldNames = new ArrayList<>(csv.next());

            while (csv.hasNext()) {
                List <String> x = csv.next();
                Map < String, String > obj = new LinkedHashMap<>();
                for (int i = 0; i < fieldNames.size(); i++) {
                    obj.put(fieldNames.get(i), x.get(i));
                }
                list.add(obj);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(System.out, list);
        }catch (Exception e){
            log.info("could not process: reason - {}",e.getCause());
        }

        return list;
    }
}
