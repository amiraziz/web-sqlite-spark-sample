package org.sample.webmetric.click;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ad-click")
@RequiredArgsConstructor
public class AdClickController {

    private final AdClickService adClickService;
    private final AdClicksMapper mapper;

    @PostMapping("/files/upload")
    public void importFileData(@RequestParam("file") MultipartFile file) {
        adClickService.importFileData(file);
    }


    @GetMapping("/save")
    public AdClickDto save(AdClickDto dto) {
        return mapper.toViewModel(adClickService.save(mapper.toEntity(dto)));
    }
}
