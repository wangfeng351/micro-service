package com.soft1851.spring.cloud.listener;

import com.soft1851.spring.cloud.domain.dto.UserAddBonusMsgDTO;
import com.soft1851.spring.cloud.domain.entity.BonusEventLog;
import com.soft1851.spring.cloud.domain.entity.User;
import com.soft1851.spring.cloud.mapper.BonusEventLogMapper;
import com.soft1851.spring.cloud.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/7
 * @Version 1.0
 */
@Service
@RocketMQMessageListener(consumerGroup = "consumer", topic = "hello-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {
    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        //1.为用户加积分
        Integer userId = userAddBonusMsgDTO.getUserId();
        User user = this.userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + userAddBonusMsgDTO.getBounds());
        this.userMapper.updateByPrimaryKeySelective(user);
        System.out.println("受到通知: " + userAddBonusMsgDTO.getBounds());
        //2.写积分日志
        this.bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(userId)
                .value(userAddBonusMsgDTO.getBounds())
                .event("CONTRIBUTE")
                .createTime(Timestamp.valueOf(LocalDateTime.now()))
                .description("投稿")
                .build());
    }
}
