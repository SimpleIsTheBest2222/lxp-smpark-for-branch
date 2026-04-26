package com.smpark.jdbc.lxp.instructor.view;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.smpark.jdbc.lxp.instructor.controller.InstructorController;
import com.smpark.jdbc.lxp.instructor.model.Instructor;
import com.smpark.jdbc.lxp.instructor.model.InstructorDTO;

public class InstructorView {

	private final InstructorController instructorController;

	public InstructorView(Connection connection) {
		this.instructorController = new InstructorController(connection);
	}

	public void showInstructorMenu(Scanner sc) {
		while (true) {
			System.out.println("""
============================================================
                           강사 관리
============================================================

  1. 강사 등록
  2. 강사 조회
  3. 뒤로 가기

------------------------------------------------------------""");
			System.out.print("> ");

			int selectedNum = readMenuNumber(sc);

			if (selectedNum == 1) {
				showInstructorCreateMenu(sc);
			} else if (selectedNum == 2) {
				showInstructorListMenu(sc);
			} else if (selectedNum == 3) {
				return;
			} else {
				System.out.println("[오류] 올바른 번호를 입력해 주세요.");
			}
		}
	}

	private void showInstructorCreateMenu(Scanner sc) {
		try {
			System.out.println("""
============================================================
                           강사 등록
============================================================

  강사 정보를 입력하세요.
""");

			InstructorDTO dto = new InstructorDTO();

			System.out.print("  이름  : ");
			dto.setName(sc.nextLine());

			System.out.print("  소개  : ");
			dto.setIntroduction(sc.nextLine());

			long id = instructorController.createInstructor(dto);

			System.out.println("------------------------------------------------------------");
			System.out.println("  강사가 등록되었습니다. id: " + id);
			System.out.println("------------------------------------------------------------");

		} catch (Exception e) {
			System.out.println("[오류] " + e.getMessage());
		}
	}

	private void showInstructorListMenu(Scanner sc) {
		try {
			List<Instructor> instructors = instructorController.findAllInstructors();

			if (instructors == null || instructors.isEmpty()) {
				System.out.println("[오류] 등록된 강사가 없습니다.");
				return;
			}

			while (true) {
				System.out.println("""
============================================================
                           강사 목록
============================================================""");

				for (Instructor instructor : instructors) {
					System.out.println("  " + instructor.getId() + ". " + instructor.getName());
				}

				System.out.println("""
------------------------------------------------------------

  1. 강사 선택
  2. 뒤로 가기

------------------------------------------------------------""");
				System.out.print("> ");

				int selectedNum = readMenuNumber(sc);

				if (selectedNum == 1) {
					System.out.print("조회할 강사 id를 입력하세요: ");
					long instructorId = Long.parseLong(sc.nextLine());
					showInstructorDetailMenu(sc, instructorId);

					instructors = instructorController.findAllInstructors();
					if (instructors == null || instructors.isEmpty()) {
						return;
					}
				} else if (selectedNum == 2) {
					return;
				} else {
					System.out.println("[오류] 올바른 번호를 입력해 주세요.");
				}
			}

		} catch (Exception e) {
			System.out.println("[오류] " + e.getMessage());
		}
	}

	private void showInstructorDetailMenu(Scanner sc, long instructorId) {
		while (true) {
			try {
				Instructor instructor = instructorController.findInstructor(instructorId);

				System.out.println("""
============================================================
                           강사 상세
============================================================""");
				System.out.println();
				System.out.println("  강사 id  : " + instructor.getId());
				System.out.println("  이름     : " + instructor.getName());
				System.out.println("  소개     : " + instructor.getIntroduction());
				System.out.println("""
------------------------------------------------------------

  1. 강사 수정
  2. 강사 삭제
  3. 뒤로 가기

------------------------------------------------------------""");
				System.out.print("> ");

				int selectedNum = readMenuNumber(sc);

				if (selectedNum == 1) {
					showInstructorUpdateMenu(sc, instructor);
				} else if (selectedNum == 2) {
					instructorController.deleteInstructor(instructor.getId());
					System.out.println("------------------------------------------------------------");
					System.out.println("  삭제가 완료되었습니다. id: " + instructor.getId());
					System.out.println("------------------------------------------------------------");
					return;
				} else if (selectedNum == 3) {
					return;
				} else {
					System.out.println("[오류] 올바른 번호를 입력해 주세요.");
				}
			} catch (Exception e) {
				System.out.println("[오류] " + e.getMessage());
				return;
			}
		}
	}

	private void showInstructorUpdateMenu(Scanner sc, Instructor instructor) {
		try {
			InstructorDTO dto = new InstructorDTO();
			dto.setId(instructor.getId());

			System.out.println("""
============================================================
                           강사 수정
============================================================

  빈 값 입력 시 기존 값이 유지됩니다.
------------------------------------------------------------""");

			System.out.print("\n  이름    : ");
			String name = sc.nextLine();
			dto.setName(name.isBlank() ? instructor.getName() : name);

			System.out.print("  소개    : ");
			String introduction = sc.nextLine();
			dto.setIntroduction(introduction.isBlank() ? instructor.getIntroduction() : introduction);

			instructorController.updateInstructor(dto);

			System.out.println("------------------------------------------------------------");
			System.out.println("  수정되었습니다.");
			System.out.println("------------------------------------------------------------");

		} catch (Exception e) {
			System.out.println("[오류] " + e.getMessage());
		}
	}

	private int readMenuNumber(Scanner sc) {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}
}


