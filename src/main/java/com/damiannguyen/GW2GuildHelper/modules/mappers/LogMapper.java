package com.damiannguyen.GW2GuildHelper.modules.mappers;

import com.damiannguyen.GW2GuildHelper.modules.guild.Guild;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.Item;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.ItemRepository;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.Log;
import com.damiannguyen.GW2GuildHelper.modules.guild.log.LogPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogMapper {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Log map(LogPojo logPojo, Guild guild){
        Item item;
        if(logPojo.getItemId() != null ) {
            if (itemRepository.existsById(logPojo.getItemId())) {
                item = itemRepository.getOne(logPojo.getItemId());
            } else {
                item = itemMapper.map(logPojo.getItemId());
                itemRepository.save(item);
            }
        }else{
            item = new Item(0L, "", "");
        }

        return new Log(
                logPojo.getId(),
                guild,
                logPojo.getTime(),
                logPojo.getUser(),
                logPojo.getType(),
                logPojo.getInvitedBy(),
                logPojo.getKickedBy(),
                logPojo.getChangedBy(),
                logPojo.getOldRank(),
                logPojo.getNewRank(),
                item,
                logPojo.getCount(),
                logPojo.getOperation(),
                logPojo.getCoins(),
                logPojo.getMotd(),
                logPojo.getAction(),
                logPojo.getUpgradeId(),
                logPojo.getRecipeId());
    }
}
