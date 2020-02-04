package com.paperpublish.repository;

import com.paperpublish.model.notifications.Notifications;
import com.paperpublish.model.notifications.ObjectFactory;
import com.paperpublish.model.notifications.TNotification;
import com.paperpublish.model.sciencepapers.TSciencePaper;
import com.paperpublish.utils.ConnectionProperties;
import com.paperpublish.utils.XUpdateTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

@Component
public class NotificationsRepository {
    @Autowired
    @Lazy
    Collection collection;

    public Long sendNotification(TSciencePaper sciencePaper){
        XUpdateQueryService updateQueryService = ConnectionProperties.getXUpdateQueryService(collection);
        TNotification notification = new ObjectFactory().createTNotification();
        notification.setNewState(sciencePaper.getStatus());
        notification.setPaperName(sciencePaper.getPaperData().getTitle().getValue());
        JAXBElement<TNotification> jaxbElement = new JAXBElement<TNotification>(
                new QName("Notification"), TNotification.class, notification);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConnectionProperties.PACKAGE_PATH + ConnectionProperties.NOTIFICATIONS_PACKAGE);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            marshaller.marshal(jaxbElement, writer);

            long res = updateQueryService.updateResource(ConnectionProperties.NOTIFICATIONS_ID,
                    String.format(XUpdateTemplate.APPEND, ConnectionProperties.NOTIFICATIONS_NAMESPACE, "//Notifications", writer.toString()));

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }
}
