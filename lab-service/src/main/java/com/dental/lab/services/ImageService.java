package com.dental.lab.services;

import com.dental.lab.dto.ImagesRequest;
import com.dental.lab.model.*;
import com.dental.lab.repository.*;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final LabService labService;

    public Images addImages(ImagesRequest imagesRequest) throws Exception {
        byte[] logoBytes = Base64.getDecoder().decode(imagesRequest.getLogoImage());
        byte[] signatureBytes = Base64.getDecoder().decode(imagesRequest.getSignatureImage());
        Images images = new Images();
        images.setLogoImage(logoBytes);
        images.setSignatureImage(signatureBytes);
        images.setLab(labService.getLabById(imagesRequest.getLabId()));
        return imageRepository.save(images);
    }

    public Images updateImages(ImagesRequest imagesRequest) throws Exception {
        Optional<Images> images = imageRepository.findByLabId(imagesRequest.getLabId());
        if (images.isPresent()) {
            byte[] logoBytes = Base64.getDecoder().decode(imagesRequest.getLogoImage());
            byte[] signatureBytes = Base64.getDecoder().decode(imagesRequest.getSignatureImage());
            images.get().setLogoImage(logoBytes);
            images.get().setSignatureImage(signatureBytes);
            return imageRepository.save(images.get());
        } else {
            throw new NotFoundException();
        }
    }

    public String deleteImages(String id) throws Exception {
        Optional<Images> images = imageRepository.findByLabId(id);
        if (images.isPresent()) {
            imageRepository.delete(images.get());
            return "Image deleted successfully";
        } else {
            throw new NotFoundException();
        }
    }

    public Images getImages(String labId) {
        Optional<Images> images = imageRepository.findByLabId(labId);
        if (images.isPresent()) {
            return images.get();
        } else {
            throw new NotFoundException();
        }
    }
}
