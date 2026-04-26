package com.smpark.jdbc.lxp;

import java.sql.Connection;
import java.util.Scanner;

import com.smpark.jdbc.lxp.config.JDBCConnection;
// course 브랜치에 올릴 예정
// import com.smpark.jdbc.lxp.course.view.CourseView;
import com.smpark.jdbc.lxp.instructor.view.InstructorView;

public class Main {

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in); Connection connection = JDBCConnection.getConnection();) {
			// course 브랜치에 올릴 예정
			// CourseView courseView = new CourseView(connection);
			InstructorView instructorView = new InstructorView(connection);

			while (true) {

				printMainMenu();

				int selectedNum = readMenuNumber(sc);

				if (selectedNum == 1) {
					// course 브랜치에 올릴 예정
					// courseView.showCourseMenu(sc);

				} else if (selectedNum == 2) {
					instructorView.showInstructorMenu(sc);

				} else if (selectedNum == 3) {
					System.out.println("종료합니다.");
					break;

				} else {
					System.out.println("[오류] 올바른 번호를 입력해 주세요.");
				}
			}

		} catch (Exception e) {
			System.out.println("[오류] 프로그램 실행 중 오류 발생");
			System.out.println(e.getMessage());
		}
	}

	private static void printMainMenu() {
		System.out.println("""
			============================================================
			                          강의 관리 콘솔
			============================================================
			
			  1. 강의 관리
			  2. 강사 관리
			  3. 종료
			
			------------------------------------------------------------""");
		System.out.print("> ");
	}

	private static int readMenuNumber(Scanner sc) {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}
}