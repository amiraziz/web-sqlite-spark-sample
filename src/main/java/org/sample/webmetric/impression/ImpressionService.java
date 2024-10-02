package org.sample.webmetric.impression;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImpressionService {
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final ImpressionRepo repo;
    private final ImpressionMapper mapper;

    @Transactional
    public void importFileData(MultipartFile file) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
            String jsonFileString = new String(byteArrayInputStream.readAllBytes());
            List<ImpressionDto> impressions = OBJECT_MAPPER.readValue(jsonFileString, new TypeReference<>() {
            });

            List<Impression> entities = mapper.toEntity(impressions);
            entities.stream().filter(f -> f.getId() == null).forEach(s -> s.setId(UUID.randomUUID().toString()));

            repo.saveAll(entities);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public Impression save(Impression impression) {
        impression.setId(UUID.randomUUID().toString());
        return repo.save(impression);
    }
}
