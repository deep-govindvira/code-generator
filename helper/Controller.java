import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*")  // Allow all origins for this controller
@RestController
public class GGGGGGGGGGController implements GGGGGGGGGGsApi {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<GGGGGGGGGGDTO> createGGGGGGGGGG(GGGGGGGGGGDTO SLLLLLLLLLDTO) {
        log.info("Creating a new GGGGGGGGGG : {}", SLLLLLLLLLDTO);
        String id = UUID.randomUUID().toString();
        SLLLLLLLLLDTO.setId(id);
        GGGGGGGGGG SLLLLLLLLL = modelMapper.map(SLLLLLLLLLDTO, GGGGGGGGGG.class);
        dynamoDBMapper.save(SLLLLLLLLL);
        log.info("GGGGGGGGGG created successfully with ID: {}", id);
        return ResponseEntity.ok(SLLLLLLLLLDTO);
    }

    @Override
    public ResponseEntity<GGGGGGGGGGDTO> deleteGGGGGGGGGGById(String id) {
        log.info("Deleting GGGGGGGGGG with ID: {}", id);
        GGGGGGGGGG SLLLLLLLLL = dynamoDBMapper.load(GGGGGGGGGG.builder().id(id).build());
        if (SLLLLLLLLL == null) {
            log.warn("GGGGGGGGGG with ID: {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
        dynamoDBMapper.delete(SLLLLLLLLL);
        GGGGGGGGGGDTO SLLLLLLLLLDTO = modelMapper.map(SLLLLLLLLL, GGGGGGGGGGDTO.class);
        log.info("GGGGGGGGGG with ID: {} deleted successfully", id);
        return ResponseEntity.ok(SLLLLLLLLLDTO);
    }

    @Override
    public ResponseEntity<List<GGGGGGGGGGDTO>> getAllGGGGGGGGGG(Integer page, Integer limit) {
        log.info("Fetching GGGGGGGGGGs with page: {}, limit: {}", page, limit);

        // Defensive defaults
        if (page == null || page < 1) page = 1;
        if (limit == null || limit < 1) limit = 10;

        // Perform full table scan (not recommended for large datasets)
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<GGGGGGGGGG> SLLLLLLLLLs = dynamoDBMapper.scan(GGGGGGGGGG.class, scanExpression);

        // Apply manual pagination
        int fromIndex = (page - 1) * limit;
        int toIndex = Math.min(fromIndex + limit, SLLLLLLLLLs.size());

        if (fromIndex >= SLLLLLLLLLs.size()) {
            log.warn("No GGGGGGGGGGs found for the given pagination parameters (page: {}, limit: {})", page, limit);
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<GGGGGGGGGGDTO> SLLLLLLLLLDTOs = SLLLLLLLLLs.subList(fromIndex, toIndex)
                .stream()
                .map(GGGGGGGGGG -> modelMapper.map(GGGGGGGGGG, GGGGGGGGGGDTO.class))
                .collect(Collectors.toList());

        log.info("Fetched {} GGGGGGGGGGs for page: {}, limit: {}", SLLLLLLLLLDTOs.size(), page, limit);
        return ResponseEntity.ok(SLLLLLLLLLDTOs);
    }

    @Override
    public ResponseEntity<GGGGGGGGGGDTO> getGGGGGGGGGGById(String id) {
        log.info("Fetching GGGGGGGGGG with ID: {}", id);
        GGGGGGGGGG SLLLLLLLLL = dynamoDBMapper.load(GGGGGGGGGG.builder().id(id).build());
        if (SLLLLLLLLL == null) {
            log.warn("GGGGGGGGGG with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
        GGGGGGGGGGDTO SLLLLLLLLLDTO = modelMapper.map(SLLLLLLLLL, GGGGGGGGGGDTO.class);
        log.info("Fetched GGGGGGGGGG with ID: {}", id);
        return ResponseEntity.ok(SLLLLLLLLLDTO);
    }

    @Override
    public ResponseEntity<GGGGGGGGGGDTO> updateGGGGGGGGGGById(String id, GGGGGGGGGGDTO SLLLLLLLLLDTO) {
        log.info("Updating GGGGGGGGGG with ID: {}", id);
        SLLLLLLLLLDTO.setId(id);
        GGGGGGGGGG SLLLLLLLLL = modelMapper.map(SLLLLLLLLLDTO, GGGGGGGGGG.class);
        GGGGGGGGGG existingGGGGGGGGGG = dynamoDBMapper.load(GGGGGGGGGG.builder().id(id).build());

        if (existingGGGGGGGGGG == null) {
            log.warn("GGGGGGGGGG with ID: {} not found for update", id);
            return ResponseEntity.notFound().build();
        }

        dynamoDBMapper.delete(existingGGGGGGGGGG);  // Deleting the existing GGGGGGGGGG before saving the updated one
        dynamoDBMapper.save(SLLLLLLLLL);
        log.info("GGGGGGGGGG with ID: {} updated successfully", id);
        return ResponseEntity.ok(SLLLLLLLLLDTO);
    }
}
