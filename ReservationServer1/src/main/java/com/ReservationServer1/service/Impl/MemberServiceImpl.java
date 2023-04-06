package com.ReservationServer1.service.Impl;

import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.Entity.MemberEntity;
import com.ReservationServer1.exception.MemberException;
import com.ReservationServer1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberDAO memberDAO;

    @Override
    public MemberDTO registerMember(MemberDTO member) {
        logger.info("[MemberService] registerMember(회원가입) 호출");
        if (memberDAO.existsById(member.getUserId())) {
            throw new MemberException("이미 존재하는 ID입니다: " + member.getUserId());
        }
        MemberEntity entity = new MemberEntity(member);
        MemberEntity result = memberDAO.create(entity);
        logger.info("[MemberService] registerMember 성공: {}", result);
        return result.toDomain();
    }

    @Override
    public MemberDTO loginMember(String userId, String userPwd) {
        logger.info("[MemberService] loginMember(로그인) 호출");
        if (!memberDAO.existsById(userId)) {
            throw new MemberException("존재하지 않는 ID입니다 : " + userId);
        }
        MemberEntity entity = memberDAO.infoById(userId);
        if (!entity.getUserPwd().equals(userPwd)) {
            throw new MemberException("잘못된 비밀번호입니다");
        }
        logger.info("[MemberService] loginMember 성공: {}", entity);
        return entity.toDomain();
    }

}
