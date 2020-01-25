package com.paperpublish.service;

import com.paperpublish.model.DTO.TSciencePaperDTO;
import com.paperpublish.model.DTO.XMLDTO;
import com.paperpublish.model.users.User;
import com.paperpublish.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paperpublish.model.sciencepapers.ObjectFactory;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.repository.SciencePapersRepository;

import java.util.List;

@Service
public class SciencePapersService {
    @Autowired
    SciencePapersRepository sciencePapersRepository;

    @Autowired
	UsersRepository usersRepository;

    public List<TSciencePaper> getAll(){
        List<TSciencePaper> papers = null;
        try {
            papers = sciencePapersRepository.getAllInProcedure();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return papers;
    }

    public List<User> getProposals(String documentId) throws Exception {
    	List<User> users = null;
		try {
			TSciencePaper paper = sciencePapersRepository.findByDocumentId(documentId);
			users = usersRepository.getProposals(paper);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return users;
	}

    public List<TSciencePaper> getAllJsonAndFilter(String query) throws Exception {
    	return sciencePapersRepository.getAllJsonAndFilter(query);
	}

	public Long delete(String documentId) throws Exception {
		try {
			return sciencePapersRepository.deleteLogical(documentId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public TSciencePaper findByDocumentId(String documentId) throws Exception {
		try {
			return sciencePapersRepository.findByDocumentId(documentId);
		} catch (Exception e) {
			throw e;
		}
	}

	public TSciencePaper getOneXML(String documentId) throws Exception {
		try {
			return sciencePapersRepository.findByDocumentId(documentId);
		} catch (Exception e) {
			throw e;
		}
	}

	public Long create(TSciencePaperDTO sciencePaperDTO) throws Exception {
		try {
			TSciencePaper sciencePaper = (new ObjectFactory()).createTSciencePaper();
			sciencePaper.setAccepted(sciencePaperDTO.getAccepted());
			sciencePaper.setCitations(sciencePaperDTO.getCitations());
			sciencePaper.setDocumentId(sciencePaperDTO.getDocumentId());
			sciencePaper.setDocumentType(sciencePaperDTO.getDocumentType());
			sciencePaper.setPaperData(sciencePaperDTO.getPaperData());
			sciencePaper.setPp(sciencePaperDTO.getPp());
			sciencePaper.setReceived(sciencePaperDTO.getReceived());
			sciencePaper.setRevised(sciencePaperDTO.getRevised());
			return sciencePapersRepository.create(sciencePaper);
		} catch (Exception e) {
			throw e;
		}
	}

	public Long createFromXml(String xml) {
    	return sciencePapersRepository.createFromXML(xml);
	}

	public void update(XMLDTO paperXMLDTO) throws Exception {
		try {

			sciencePapersRepository.update(paperXMLDTO);
		} catch (Exception e) {
			throw e;
		}
		
	}

	public List<TSciencePaper> getByAuthorUsername(String username) {
		List<TSciencePaper> papers = null;
        try {
            papers = sciencePapersRepository.getByAuthorUsername(username);
        }catch (Exception e){
        	e.printStackTrace();
        }
        return papers;
		
	}
}
