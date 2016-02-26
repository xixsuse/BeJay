package rocks.itsnotrocketscience.bejay.search.contract;

import rocks.itsnotrocketscience.bejay.music.model.Model;

public interface MusicSearchContract {
    void searchTracks(@Model.Type int type, String query);
    void onModelSelected(Model model);
}
