package com.demoApp.admin.service;

import com.demoApp.admin.dto.ScheduledTaskDTO;
import com.demoApp.admin.entity.ScheduledTask;
import com.demoApp.admin.repository.ScheduledTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduledTaskService {

    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;

    // Create or Update a Scheduled Task
    public ScheduledTask saveScheduledTask(ScheduledTask scheduledTask) {
        scheduledTask.setUpdatedAt(LocalDateTime.now());
        if (scheduledTask.getCreatedAt() == null) {
            scheduledTask.setCreatedAt(LocalDateTime.now());
        }
        return scheduledTaskRepository.save(scheduledTask);
    }

    // Get task by ID
    public ScheduledTask getScheduledTaskById(Long id) {
        Optional<ScheduledTask> task = scheduledTaskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException("Scheduled Task not found with id: " + id));
    }

    // Get all tasks
    public List<ScheduledTask> getAllScheduledTasks() {
        return scheduledTaskRepository.findAll();
    }

    // Delete task by ID
    public void deleteScheduledTask(Long id) {
        scheduledTaskRepository.deleteById(id);
    }

    // Update task status and result after execution
    @Transactional
    public ScheduledTask updateTaskExecution(Long id, ScheduledTask.TaskStatus newStatus, String executionResult) {
        ScheduledTask task = getScheduledTaskById(id);
        task.setStatus(newStatus);
        task.setLastExecutionResult(executionResult);
        task.setLastExecutionTime(LocalDateTime.now());

        if (newStatus == ScheduledTask.TaskStatus.COMPLETED || newStatus == ScheduledTask.TaskStatus.FAILED) {
            task.setNextExecutionTime(LocalDateTime.now().plusHours(24)); // Reschedule next execution
        }

        return scheduledTaskRepository.save(task);
    }

    // Periodically check for due tasks and execute them
    @Scheduled(fixedRate = 60000) // every 60 seconds
    @Transactional
    public void checkScheduledTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledTask> dueTasks = scheduledTaskRepository.findByNextExecutionTimeBefore(now);

        for (ScheduledTask task : dueTasks) {
            if (task.getStatus() == ScheduledTask.TaskStatus.ACTIVE) {
                try {
                    // Simulated task execution logic (replace with actual logic)
                    Thread.sleep(2000);
                    updateTaskExecution(task.getId(), ScheduledTask.TaskStatus.COMPLETED, "Task executed successfully");
                } catch (Exception e) {
                    updateTaskExecution(task.getId(), ScheduledTask.TaskStatus.FAILED, "Execution failed: " + e.getMessage());
                }
            }
        }
    }

    // Get tasks by status
    public List<ScheduledTask> getScheduledTasksByStatus(ScheduledTask.TaskStatus status) {
        return scheduledTaskRepository.findByStatus(status);
    }

    public Page<ScheduledTask> getScheduledTasksByStatusPaged(ScheduledTask.TaskStatus status, Pageable pageable) {
        return scheduledTaskRepository.findByStatusOrderByNextExecutionTimeAsc(status, pageable);
    }

    public List<ScheduledTask> getScheduledTasksByService(String serviceName) {
        return scheduledTaskRepository.findByServiceName(serviceName);
    }

    public Page<ScheduledTask> getScheduledTasksByServicePaged(String serviceName, Pageable pageable) {
        return scheduledTaskRepository.findByServiceNameOrderByNextExecutionTimeAsc(serviceName, pageable);
    }

    // ➕ Added method: Convert Entity to DTO
    public ScheduledTaskDTO convertToDTO(ScheduledTask task) {
        ScheduledTaskDTO dto = new ScheduledTaskDTO();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setServiceName(task.getServiceName());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setNextExecutionTime(task.getNextExecutionTime());
        dto.setLastExecutionTime(task.getLastExecutionTime());
        dto.setLastExecutionResult(task.getLastExecutionResult());
        return dto;
    }

    // ➕ Added method: Convert List of Entities to DTOs
    public List<ScheduledTaskDTO> convertToDTOs(List<ScheduledTask> tasks) {
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Optional: create/update method (if needed in controller)
    public ScheduledTask createScheduledTask(ScheduledTaskDTO dto) {
        ScheduledTask task = new ScheduledTask();
        task.setTaskName(dto.getTaskName());
        task.setServiceName(dto.getServiceName());
        task.setStatus(dto.getStatus());
        task.setCreatedAt(LocalDateTime.now());
        task.setNextExecutionTime(dto.getNextExecutionTime());
        return scheduledTaskRepository.save(task);
    }

    public ScheduledTask updateScheduledTask(Long id, ScheduledTaskDTO dto) {
        ScheduledTask task = getScheduledTaskById(id);
        task.setTaskName(dto.getTaskName());
        task.setServiceName(dto.getServiceName());
        task.setStatus(dto.getStatus());
        task.setNextExecutionTime(dto.getNextExecutionTime());
        task.setUpdatedAt(LocalDateTime.now());
        return scheduledTaskRepository.save(task);
    }
}
