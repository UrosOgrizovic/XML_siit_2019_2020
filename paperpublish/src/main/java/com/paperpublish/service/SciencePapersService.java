package com.paperpublish.service;

import com.paperpublish.model.DTO.TSciencePaperDTO;
import com.paperpublish.model.DTO.XMLDTO;
import com.paperpublish.model.sciencepapers.TReviewers;
import com.paperpublish.model.users.User;
import com.paperpublish.repository.UsersRepository;
import org.apache.fop.area.RegionViewport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paperpublish.model.sciencepapers.ObjectFactory;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.repository.SciencePapersRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SciencePapersService {
    @Autowired
    SciencePapersRepository sciencePapersRepository;

    @Autowired
	UsersRepository usersRepository;

    public List<TSciencePaper> getAllAccepted(){
        List<TSciencePaper> papers = null;
        try {
            papers = sciencePapersRepository.getAllAccepted();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return papers;
    }

    public List<TSciencePaper> getAll() {
    	List<TSciencePaper> papers = null;
    	try {
    		papers = sciencePapersRepository.getAll();
		} catch (Exception e){
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

			sciencePapersRepository.updateXML(paperXMLDTO);
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

	public TSciencePaper addReviewer(String documentId, String userName) throws Exception {
    	TSciencePaper paper = sciencePapersRepository.findByDocumentId(documentId);
		TReviewers reviewer = new TReviewers();
		reviewer.getReviewerUserName().add(userName);
    	paper.getPaperData().getReviewer().add(reviewer);
    	sciencePapersRepository.update(paper);
    	return paper;
	}

	public List<TSciencePaper> getForReview(String userName) {
		List<TSciencePaper> papers = null;
		try {
			papers = sciencePapersRepository.getForReview(userName);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return papers;
	}

	public TSciencePaper changeStatus(String documentId, String status) throws Exception {
		TSciencePaper paper = sciencePapersRepository.findByDocumentId(documentId);
		paper.setStatus(status);
		sciencePapersRepository.update(paper);
		return paper;
	}

	/**
	 * 
	 * @param keywords
	 * @param paperPublishDate
	 * @param authorUserName
	 * @return list of science paper titles that match criteria
	 */
	public List<String> searchByMetadata(String keywords, Date paperPublishDate, String authorUserName, boolean searchOnlyMyPapers) {
		return sciencePapersRepository.searchByMetadata(keywords, paperPublishDate, authorUserName, searchOnlyMyPapers);
	}

	/**
	 * 
	 * @param text
	 * @param authorUserName
	 * @return list of science paper titles that match criteria
	 */
	public List<String> searchByText(String text, String authorUserName, boolean searchOnlyMyPapers) {
		return sciencePapersRepository.searchByText(text, authorUserName, searchOnlyMyPapers);		
	}

}
