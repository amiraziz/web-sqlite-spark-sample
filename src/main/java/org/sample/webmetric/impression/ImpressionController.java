package org.sample.webmetric.impression;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/impression")
@RequiredArgsConstructor
public class ImpressionController {

    private final ImpressionService impressionService;
    private final ImpressionMapper impressionMapper;

    @PostMapping("/files/upload")
    public void importFileData(@RequestParam("file") MultipartFile file) {
        impressionService.importFileData(file);
    }

    @PostMapping("/save")
    public ImpressionDto save(@Valid @RequestBody ImpressionDto impression) {
        return impressionMapper.toViewModel(impressionService.save(impressionMapper.toEntity(impression)));
    }
}
