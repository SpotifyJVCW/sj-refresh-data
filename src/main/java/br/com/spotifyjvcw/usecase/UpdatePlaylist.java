package br.com.spotifyjvcw.usecase;

import br.com.spotifyjvcw.domain.Token;

import java.util.List;

public interface UpdatePlaylist {

    void execute(Token token, String playlistId, List<String> trackIds);
}
