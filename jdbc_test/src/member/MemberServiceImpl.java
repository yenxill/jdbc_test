package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MemberServiceImpl implements MemberService {

	MemberDAO mDAO = new MemberDAO();
	Scanner sc = new Scanner(System.in);

	@Override
	public void startProgram() {
		int count = 0;

		while(true) {

			int choice = printMenu();

			switch(choice) {
			case 1 : 
				displayMsg("1. 회원 정보 등록");
				insertMember();
				break;

			case 2 :
				displayMsg("2. 회원 정보 수정");
				updateMember();
				break;

			case 3 : 
				displayMsg("3. 회원 정보 삭제");
				deleteMember();
				break;

			case 4 : 
				displayMsg("4. 회원 정보 출력(이름)");
				printMember();
				break;
			case 5 : 
				displayMsg("5. 회원 전체 정보 출력");
				printAllMembers();
				break;

			case 6 : 
				displayMsg("프로그램 종료");
				count++;
				break;

			default :
				//"잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능"
				break;
			}

			if (count == 1) {
				break;
			}

			//			if (choice == 6) {
			//				break;
			//			}

		}
	}

	// 0. 프로그램 메뉴 출력 및 선택
	public int printMenu() {
		displayMsg("===== 회원 관리 프로그램 =====");
		displayMsg("1. 회원 정보 등록");
		displayMsg("2. 회원 정보 수정");
		displayMsg("3. 회원 정보 삭제");
		displayMsg("4. 회원 정보 출력(이름)");
		displayMsg("5. 회원 전체 정보 출력");
		displayMsg("6. 프로그램 종료");
		System.out.print("[선택] : ");

		int choice = sc.nextInt();

		return choice;
	}

	//메제시 출력용
	public void displayMsg(String msg) {
		System.out.println(msg);
	}

	public void insertMember() {
		//		private String memberId;	// 회원 아이디
		//		private String memberPw;	// 회원 비밀번호
		//		private String memberName;	// 회원 이름
		//		private String email;		// 이메일
		//		private String phone;		// 연락처

		// 회원 정보를 등록할 member 객체 생성자
		Member member = new Member();

		// 회원 정보 입력받기
		System.out.print("회원 아이디를 입력해 주세요 : ");
		String memberId = sc.next();

		System.out.print("회원 비밀번호 : ");
		String memberPw = sc.next();

		System.out.print("회원 이름 : ");
		String memberName = sc.next();

		System.out.print("생년 월일(ex) 19900101) : ");
		String memberBirth = sc.next();

		System.out.print("이메일 주소를 입력해 주세요. : ");
		String email = sc.next();

		System.out.print("연락처 정보를 입력해 주세요. : ");
		String phone = sc.next();

		// member에 회원정보 셋팅
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setMemberBirth(memberBirth);
		member.setMemberEmail(email);
		member.setMemberPhone(phone);

		
		int chk = 0;
		
		chk = mDAO.insertMember(member);

		if(chk > 0) {
			System.out.println("등록되었습니다.");
		}else {
			System.out.println("등록에 실패하었습니다.");

		}

	}

	
	public void updateMember() {
		System.out.println("회원명을 입력하세요 >>>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();

		List<HashMap<String,Object>> memberList = new ArrayList();
		memberList = mDAO.printSearchmember(title);
		System.out.println("회원ID\t이름\t전화번호\t이메일");

		for(int i = 0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t");
			System.out.print(memberList.get(i).get("member_phone")+"\t");
			System.out.println(memberList.get(i).get("member_email"));

		}
		
		System.out.println("수정할 회원의 순번을 입력하세요>>>>>>>>>");	
		int num = sc.nextInt();
		int memberId = (int) memberList.get(num-1).get("member_idx");	
		
		System.out.println("변경될 회원명을 입력하세요>>>>>>>>>");	
		sc.nextLine();
		String updateTitle = sc.nextLine();
		
		int resultChk = 0;
		resultChk = mDAO.updateMember(memberId, updateTitle);
		
		if(resultChk>0) {
			System.out.println("회원이 수정되었습니다.");
		} else {
			System.out.println("회원 수정이 실패했습니다.");
		}

	}
	
	
//	3번
	public void deleteMember() {
		System.out.println("회원명을 입력하세요 >>>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();

		//결과값을 가지고 있음
		int resultChk = 0;
		resultChk = mDAO.deleteMember(title);
		if(resultChk>0) {
			System.out.println("회원이 삭제 되었습니다.");
		} else {
			System.out.println("회원 삭제에 실패했습니다.");
		}
		
		
		
		
		
	}
	
	
//	4번
	public void printMember() {
		List<HashMap<String,Object>> memberList = new ArrayList();
		System.out.println("검색할 회원을 입력하세요>>>>>>>");
		sc.nextLine();
		String title = sc.nextLine();
		
		
		memberList = mDAO.printSearchmember(title);
		System.out.println("회원ID\\t이름\\t전화번호\\t이메일");
		
		for(int i = 0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t");
			System.out.print(memberList.get(i).get("member_phone")+"\t");
			System.out.println(memberList.get(i).get("member_email"));

			
		}
	}
	
	
//	5번
	public void printAllMembers() {
		//List는 new ArrayList로 선언
		
		List<HashMap<String,Object>> memberList = new ArrayList();
		memberList = mDAO.printAllMembers();
		
		System.out.println("회원ID\t이름\t전화번호\t이메일");
		
		for(int i = 0; i<memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_id")+"\t");
			System.out.print(memberList.get(i).get("member_name")+"\t");
			System.out.print(memberList.get(i).get("member_phone")+"\t");
			System.out.println(memberList.get(i).get("member_email"));

			
		}
	}
}
