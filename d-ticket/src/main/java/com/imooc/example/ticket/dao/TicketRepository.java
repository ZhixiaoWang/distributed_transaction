package com.imooc.example.ticket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.imooc.example.ticket.domain.Ticket;


/**
 * Created by shawn on 2018/1/20.
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    List<Ticket> findOneByOwner(Long owner);
    
    Ticket findOneByTicketNum(Long ticketNum);
    
    Ticket findOneByTicketNumAndLockUser(Long ticketNum, Long lockUser);
    
    //是否自动维护context persistent的自动维护问题
    @Override
    @Modifying(clearAutomatically=true)
    Ticket save(Ticket ticket);
    
    //幂等性
    @Modifying(clearAutomatically=true)
    @Query("UPDATE ticket SET lockUser=?1 WHERE lockUser is NULL and ticketNum = ?2 ")
    int lockTicket(Long customerId, Long ticketNum);
    
    @Modifying(clearAutomatically=true)
    @Query("UPDATE ticket SET owner=?1, lockUser=NULL WHERE lockUser =?1 and ticketNum = ?2 ")
    int moveTicket(Long customerId, Long ticketNum);
    
    @Modifying(clearAutomatically=true)
    @Query("UPDATE ticket SET owner=NULL WHERE lockUser =?1 and ticketNum = ?2 ")
    int unMoveTicket(Long customerId, Long ticketNum);
    
    @Modifying(clearAutomatically=true)
    @Query("UPDATE ticket SET lockUser=NULL WHERE lockUser =?1 and ticketNum = ?2 ")
    int unLockTicket(Long customerId, Long ticketNum);
    
}
