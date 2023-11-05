package com.kakaoseventeen.dogwalking.dog.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.S3Uploader;
import com.kakaoseventeen.dogwalking._core.utils.exception.DogNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.ImageNotExistException;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.dto.DogReqDTO;
import com.kakaoseventeen.dogwalking.dog.dto.DogRespDTO;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Dog(반려견) 서비스
 *
 * @author 승건 이
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DogService {

    private final DogJpaRepository dogJpaRepository;

    private final S3Uploader s3Uploader;


    /**
     * 강아지 프로필 등록 메서드
     */
    @Transactional
    public DogRespDTO.save saveDog(MultipartFile image, DogReqDTO dogReqDTO, CustomUserDetails customUserDetails) throws ImageNotExistException, IOException {

        if (image == null){
            throw new ImageNotExistException(MessageCode.IMAGE_NOT_EXIST);
        }

        Member member = customUserDetails.getMember();

        String dogImage = s3Uploader.uploadFiles(member.getId(), image, "dogProfile");

        Dog dog = Dog.of(dogReqDTO, member, dogImage);


        return new DogRespDTO.save(dogJpaRepository.save(dog));
    }

    /**
     * 강아지 프로필 수정 메서드
     */
    @Transactional
    public DogRespDTO.save updateDog(Long dogId, DogReqDTO dogReqDTO, CustomUserDetails customUserDetails) throws ImageNotExistException, DogNotExistException, IOException {

        Dog dog = dogJpaRepository.findById(dogId).orElseThrow(() -> new DogNotExistException(MessageCode.DOG_NOT_EXIST));

        if (!(dogReqDTO.getImage() == null)){
            String dogImage = s3Uploader.uploadFiles(customUserDetails.getMember().getId(), dogReqDTO.getImage(), "dogProfile");
            dog.updateDog(dogReqDTO, dogImage);
        } else {
            dog.updateDog(dogReqDTO, "null");
        }

        return new DogRespDTO.save(dog);
    }


    /**
     * 강아지 프로필 조회 메서드
     */
    @Transactional(readOnly = true)
    public DogRespDTO.findById findByDogId(Long dogId) throws DogNotExistException {
        Optional<Dog> dog = dogJpaRepository.findById(dogId);

        if (dog.isPresent()) {
            return new DogRespDTO.findById(dog.get());
        }
        else {
            throw new DogNotExistException(MessageCode.DOG_NOT_EXIST);
        }
    }
}
