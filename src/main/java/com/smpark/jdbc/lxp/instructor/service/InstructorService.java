package com.smpark.jdbc.lxp.instructor.service;

import java.sql.Connection;
import java.util.List;

import com.smpark.jdbc.lxp.instructor.model.Instructor;
import com.smpark.jdbc.lxp.instructor.model.InstructorDTO;
import com.smpark.jdbc.lxp.instructor.repository.InstructorRepository;

public class InstructorService {

	private final InstructorRepository instructorRepository;

	public InstructorService(Connection connection) {
		this.instructorRepository = new InstructorRepository(connection);
	}

	public long create(InstructorDTO dto) {
		validateInstructor(dto);

		Instructor instructor = new Instructor();
		instructor.setName(dto.getName());
		instructor.setIntroduction(dto.getIntroduction());

		return instructorRepository.create(instructor);
	}

	public List<Instructor> findAll() {
		return instructorRepository.findAll();
	}

	public Instructor findById(long id) {
		Instructor instructor = instructorRepository.findById(id);
		if (instructor == null) {
			throw new IllegalArgumentException("해당 id의 강사를 찾을 수 없습니다. (id: " + id + ")");
		}
		return instructor;
	}

	public boolean existsById(long id) {
		return instructorRepository.existsById(id);
	}

	public void update(InstructorDTO dto) {
		validateInstructor(dto);

		Instructor instructor = instructorRepository.findById(dto.getId());
		if (instructor == null) {
			throw new IllegalArgumentException("해당 id의 강사를 찾을 수 없습니다. (id: " + dto.getId() + ")");
		}

		instructorRepository.update(dto);
	}

	public void delete(long id) {
		Instructor instructor = instructorRepository.findById(id);
		if (instructor == null) {
			throw new IllegalArgumentException("해당 id의 강사를 찾을 수 없습니다. (id: " + id + ")");
		}

		instructorRepository.softDelete(id);
	}

	private void validateInstructor(InstructorDTO dto) {
		if (dto.getName() == null || dto.getName().isBlank()) {
			throw new IllegalArgumentException("이름은 필수입니다.");
		}
		if (dto.getName().length() > 10) {
			throw new IllegalArgumentException("이름은 10자 이하로 입력해 주세요.");
		}
		if (dto.getIntroduction() == null || dto.getIntroduction().isBlank()) {
			throw new IllegalArgumentException("소개는 필수입니다.");
		}
		if (dto.getIntroduction().length() > 100) {
			throw new IllegalArgumentException("소개는 100자 이하로 입력해 주세요.");
		}
	}
}


