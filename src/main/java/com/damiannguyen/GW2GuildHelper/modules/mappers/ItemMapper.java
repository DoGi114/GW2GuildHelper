package com.damiannguyen.GW2GuildHelper.modules.mappers;

import com.damiannguyen.GW2GuildHelper.modules.guild.items.Item;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.ItemPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ItemMapper {
    private final Logger logger = LoggerFactory.getLogger(ItemMapper.class);

    public Item map(ItemPojo itemPojo){
        return new Item(itemPojo.getId(), itemPojo.getName(), itemPojo.getIcon());
    }

    public Item map(Long id){
        if(id == 0) return new Item(id, "", "");
        String api = "https://api.guildwars2.com/v2/items/" + id ;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ItemPojo> response =
                    restTemplate.getForEntity(
                            api,
                            ItemPojo.class);

            ItemPojo itemPojo = response.getBody();
            if(itemPojo == null){
                throw new NullPointerException("Bad response");
            }
            return new Item(itemPojo.getId(), itemPojo.getName(), itemPojo.getIcon());
        }catch (HttpClientErrorException | NullPointerException e){
            logger.error(e.getLocalizedMessage());
            return new Item(id, "", "");
        }
    }
}
