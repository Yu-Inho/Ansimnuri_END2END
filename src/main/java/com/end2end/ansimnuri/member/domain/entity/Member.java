package com.end2end.ansimnuri.member.domain.entity;

import com.end2end.ansimnuri.admin.domain.entity.Block;
import com.end2end.ansimnuri.admin.domain.entity.Complaint;
import com.end2end.ansimnuri.board.domain.entity.Qna;
import com.end2end.ansimnuri.map.domain.entity.SearchHistory;
import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.note.domain.entity.NoteRec;
import com.end2end.ansimnuri.note.domain.entity.NoteReply;
import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.util.entity.Timestamp;
import com.end2end.ansimnuri.util.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SequenceGenerator(
        name = "userSequenceGenerator",
        sequenceName = "USER_ID_SEQ",
        allocationSize = 1
)
@Table(name = "MEMBER")
@Entity
public class Member extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    private Long id;
    @Column(name = "LOGIN_ID", unique = true)
    private String loginId;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NICKNAME", unique = true)
    private String nickname;
    @Column(name = "EMAIL",  unique = true)
    private String email;
    @Column(name = "POSTCODE")
    private String postcode;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "DETAIL_ADDRESS")
    private String detailAddress;
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Column(name = "KAKAO_ID")
    private String kakaoId;

    @OneToMany(mappedBy = "member")
    private List<Qna> qnaList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Block> blockList;
    @OneToMany(mappedBy = "reporter", orphanRemoval = true)
    private List<Complaint> complaintReporterList;
    @OneToMany(mappedBy = "reportee", orphanRemoval = true)
    private List<Complaint> complaintReporteeList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Note> noteList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<NoteRec> noteRecList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<NoteReply> noteReplyList;
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<SearchHistory> searchHistoryList;



    public static Member ofKakao(String kakaoId, String nickname) {
        return Member.builder()
                .loginId(kakaoId)
                .kakaoId(kakaoId)
                .nickname(nickname)
                .email(kakaoId + "@kakao.oauth")
                .password("TEMP")
                .address("간편가입")
                .detailAddress("")
                .postcode("")
                .role(Roles.USER)
                .build();
    }



    public static Member of (MemberDTO memberDTO) {
        return Member.builder()
                .loginId(memberDTO.getLoginId())
                .password(memberDTO.getPassword())
                .nickname(memberDTO.getNickname())
                .email(memberDTO.getEmail())
                .postcode(memberDTO.getPostcode())
                .address(memberDTO.getAddress())
                .detailAddress(memberDTO.getDetailAddress())
                .role(Roles.USER)
                .kakaoId(memberDTO.getKakaoId())
                .build();
    }

    public void update(MemberDTO memberDTO) {
        this.nickname = memberDTO.getNickname();
        this.email = memberDTO.getEmail();
        this.postcode = memberDTO.getPostcode();
        this.address = memberDTO.getAddress();
        this.detailAddress = memberDTO.getDetailAddress();

    }

    public void change(String newPassword) {
        this.password = newPassword;
    }
    public void changeLoginId(String newLoginId) {
        this.loginId = newLoginId;
    }
}
