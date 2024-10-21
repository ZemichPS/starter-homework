package by.zemich.sessionauthorizationstarter.service;

import by.zemich.sessionauthorizationstarter.properties.AuthorisationBlackListProperties;
import by.zemich.sessionauthorizationstarter.service.api.BlackListDataSource;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class DefaultBlackListDataSource implements BlackListDataSource {

    private final AuthorisationBlackListProperties blackListProperties;

    @Override
    public Set<String> getBlackList() {
        return blackListProperties.getBlackList();
    }
}
