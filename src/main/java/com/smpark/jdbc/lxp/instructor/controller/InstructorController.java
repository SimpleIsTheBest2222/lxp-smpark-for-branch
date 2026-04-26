package com.smpark.jdbc.lxp.instructor.controller;

import java.sql.Connection;
import java.util.List;

import com.smpark.jdbc.lxp.instructor.model.Instructor;
import com.smpark.jdbc.lxp.instructor.model.InstructorDTO;
import com.smpark.jdbc.lxp.instructor.service.InstructorService;

public class InstructorController {

	private final InstructorService instructorService;

	public InstructorController(Connection connection) {
		this.instructorService = new InstructorService(connection);
	}

	public long createInstructor(InstructorDTO dto) {
		return instructorService.create(dto);
	}

	public List<Instructor> findAllInstructors() {
		return instructorService.findAll();
	}

	public Instructor findInstructor(long id) {
		return instructorService.findById(id);
	}

	public boolean existsInstructor(long id) {
		return instructorService.existsById(id);
	}

	public void updateInstructor(InstructorDTO dto) {
		instructorService.update(dto);
	}

	public void deleteInstructor(long id) {
		instructorService.delete(id);
	}
}


