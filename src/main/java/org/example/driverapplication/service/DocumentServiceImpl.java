package org.example.driverapplication.service;

import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.entity.Document;
import org.example.driverapplication.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class DocumentServiceImpl {
    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document getDocument(Long id) {
        return documentRepository.findById(id).get();
    }

    public Document updateDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document deleteDocument(Long id) {
        Document document = documentRepository.findById(id).get();
        documentRepository.delete(document);
        return document;
    }


}

