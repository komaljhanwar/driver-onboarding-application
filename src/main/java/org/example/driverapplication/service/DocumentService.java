/*
package org.example.driverapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
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

*/
