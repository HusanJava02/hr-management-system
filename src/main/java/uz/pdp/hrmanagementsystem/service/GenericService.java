package uz.pdp.hrmanagementsystem.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementsystem.dto.GenericResponse;
import uz.pdp.hrmanagementsystem.exceptions.ResourceNotFoundException;
import uz.pdp.hrmanagementsystem.mappers.GenericMapper;
import java.util.List;

@Service
@AllArgsConstructor
public abstract class GenericService<E, ID, M , T extends GenericMapper<E,M>, R extends JpaRepository<E,ID>> {

    protected R repository;

    protected T mapper;

    protected Class<E> eClass;

    public abstract M create(M model);

    public M findById(ID id) {
        E entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(eClass.getName()+" not found with given id: "+id));
       return mapper.toModel(entity);
    }

    public List<M> findALlPageable(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<E> entityObjectPage = repository.findAll(pageable);
        return mapper.toModelList(entityObjectPage.getContent());
    }

    public abstract M update(M model);

    public abstract GenericResponse delete(Long id);
}
