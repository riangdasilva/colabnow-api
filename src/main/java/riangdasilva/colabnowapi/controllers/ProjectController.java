package riangdasilva.colabnowapi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import riangdasilva.colabnowapi.dtos.ProjectDto;
import riangdasilva.colabnowapi.models.ProjectModel;
import riangdasilva.colabnowapi.repositories.ProjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping()
    public ResponseEntity<String> createProject(@RequestBody @Valid ProjectDto projectDto) {
        var newProject = new ProjectModel();
        BeanUtils.copyProperties(projectDto, newProject);
        projectRepository.save(newProject);
        return ResponseEntity.status(HttpStatus.CREATED).body("Project created");
    }

    @GetMapping()
    public ResponseEntity<List<ProjectModel>> readProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readProject(@PathVariable Long id) {
        Optional<ProjectModel> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectDto projectDto) {
        Optional<ProjectModel> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
        var newProject = new ProjectModel();
        BeanUtils.copyProperties(projectDto, newProject);
        newProject.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(projectRepository.save(newProject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable Long id) {
        Optional<ProjectModel> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
        projectRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }
}
