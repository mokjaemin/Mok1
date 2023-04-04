package com.ReservationServer1.data.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ReservationServer1.data.Entity.MemberEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class CustomListener {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomListener.class);

    @PostLoad
    public void postLoad(MemberEntity entity) {
        LOGGER.info("[postLoad] called!!");
    }

    @PrePersist
    public void prePersist(MemberEntity entity) {
        LOGGER.info("[prePersist] called!!");
    }

    @PostPersist
    public void postPersist(MemberEntity entity) {
        LOGGER.info("[postPersist] called!!");
    }

    @PreUpdate
    public void preUpdate(MemberEntity entity) {
        LOGGER.info("[preUpdate] called!!");
    }

    @PostUpdate
    public void postUpdate(MemberEntity entity) {
        LOGGER.info("[postUpdate] called!!");
    }

    @PreRemove
    public void preRemove(MemberEntity entity) {
        LOGGER.info("[preRemove] called!!");
    }

    @PostRemove
    public void postRemove(MemberEntity entity) {
        LOGGER.info("[postRemove] called!!");
    }
}
