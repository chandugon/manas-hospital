
package com.hms.hospital.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hms.hospital.entity.Appointment;
import com.hms.hospital.entity.Role;
import com.hms.hospital.entity.User;
import com.hms.hospital.repository.AppointmentRepository;
import com.hms.hospital.repository.PatientRepository;
import com.hms.hospital.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Only Admin can access
public class AdminController {

	private final UserRepository userRepo;
	private final PatientRepository patientRepo;
	private final AppointmentRepository appointmentRepo;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {

		model.addAttribute("totalUsers", userRepo.count());
		model.addAttribute("totalPatients", patientRepo.count());
		model.addAttribute("totalAppointments", appointmentRepo.count());
		model.addAttribute("totalDoctors", userRepo.countByRole(Role.DOCTOR));
		model.addAttribute("recentAppointments", appointmentRepo.findTop10ByOrderByStartTimeDesc());
		return "admin/dashboard";
	}

	@GetMapping("/users")
	public String manageUsers(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "admin/users";
	}

	@PostMapping("/users/{id}/delete")
	public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {

		User user = userRepo.findById(id).orElseThrow();

		// Delete patient and related appointments
		if (user.getRole() == Role.PATIENT) {

			patientRepo.findByUserEmail(user.getEmail()).ifPresent(patient -> {

				// Delete all appointments belonging to this patient
				List<Appointment> appointments = appointmentRepo.findByPatientId(patient.getId());

				appointmentRepo.deleteAll(appointments);

				// Delete patient
				patientRepo.delete(patient);
			});
		}

		// Delete user
		userRepo.delete(user);

		ra.addFlashAttribute("msg", "User deleted successfully!");
		return "redirect:/admin/users";
	}

	@PostMapping("/appointments/{id}/complete")
	public String completeAppointment(@PathVariable Long id, RedirectAttributes ra) {

		Appointment appointment = appointmentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));

		appointment.setStatus("COMPLETED");
		appointmentRepo.save(appointment);

		ra.addFlashAttribute("msg", "Appointment marked as completed successfully!");

		return "redirect:/admin/appointments";
	}

	@PostMapping("/users/{id}/role")
	public String changeRole(@PathVariable Long id, @RequestParam Role role, RedirectAttributes ra) {
		User user = userRepo.findById(id).orElseThrow();
		user.setRole(role);
		userRepo.save(user);
		ra.addFlashAttribute("msg", "Role changed to " + role + " successfully!");
		return "redirect:/admin/users";
	}

	@GetMapping("/appointments")
	public String allAppointments(Model model) {
		model.addAttribute("appointments", appointmentRepo.findAllByOrderByStartTimeDesc());
		return "admin/appointments";
	}

	@PostMapping("/appointments/{id}/delete")
	public String deleteAppointment(@PathVariable Long id, RedirectAttributes ra) {

		appointmentRepo.deleteById(id);

		ra.addFlashAttribute("msg", "Appointment deleted successfully!");
		return "redirect:/admin/appointments";
	}

}