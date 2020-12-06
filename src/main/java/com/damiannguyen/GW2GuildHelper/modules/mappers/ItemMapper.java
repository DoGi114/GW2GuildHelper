package com.damiannguyen.GW2GuildHelper.modules.mappers;

import com.damiannguyen.GW2GuildHelper.modules.guild.items.Item;
import com.damiannguyen.GW2GuildHelper.modules.guild.items.ItemPojo;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class ItemMapper {

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
            log.error(e.getLocalizedMessage());
            return new Item(id, "", "");
        }
    }
}
