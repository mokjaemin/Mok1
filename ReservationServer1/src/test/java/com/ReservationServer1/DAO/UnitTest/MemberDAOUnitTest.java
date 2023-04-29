package com.ReservationServer1.DAO.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ReservationServer1.DAO.JPAImpl.Repository.MemberRepository;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;

@SpringBootTest
public class MemberDAOUnitTest {
	
	@Autowired
    MemberRepository memberRepository;
	
	@DisplayName("회원 가입")
    @Test
    void addUser() {
        // given
		MemberDTO sampleMember = MemberDTO.sample();
		MemberEntity sampleEntity = new MemberEntity(sampleMember);
        
        // when
        MemberEntity savedMember = memberRepository.save(sampleEntity);

        // then
        assertThat(savedMember.getUserId()).isEqualTo(sampleEntity.getUserId());
        assertThat(savedMember.getUserPwd()).isEqualTo(sampleEntity.getUserPwd());
        assertThat(savedMember.getUserName()).isEqualTo(sampleEntity.getUserName());
        assertThat(savedMember.getUserEmail()).isEqualTo(sampleEntity.getUserEmail());
        assertThat(savedMember.getUserNumber()).isEqualTo(sampleEntity.getUserNumber());
        assertThat(savedMember.getUserAddress()).isEqualTo(sampleEntity.getUserAddress());
    }

}
