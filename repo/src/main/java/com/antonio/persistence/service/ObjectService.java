package com.antonio.persistence.service;


import com.antonio.persistence.entity.ObjectToGenerator;
import com.antonio.persistence.repository.ObjectToGeneratorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectService {

    private final ObjectToGeneratorRepository repository;

    public ObjectService(ObjectToGeneratorRepository repository) {
        this.repository = repository;
    }

    // ✅ Сохранение объекта
    public ObjectToGenerator saveObject(ObjectToGenerator object) {
        return repository.save(object);
    }

    // ✅ Получение объекта по ID
    public Optional<ObjectToGenerator> getObjectById(Long id) {
        return repository.findById(id);
    }

    // ✅ Удаление объекта по ID
    public void deleteObjectById(Long id) {
        repository.deleteById(id);
    }

    // ✅ Получение всех объектов
    public List<ObjectToGenerator> getAllObjects() {
        return repository.findAll();
    }

    // ✅ Обновление полей объекта (пример)
    public ObjectToGenerator updateObject(Long id, ObjectToGenerator updatedData) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setKeyName(updatedData.getKeyName());
                    existing.setRusName(updatedData.getRusName());
                    existing.setTo1C8name(updatedData.getTo1C8name());
                    existing.setImagePath(updatedData.getImagePath());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Object not found with ID: " + id));
    }
}

