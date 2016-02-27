package rocks.itsnotrocketscience.bejay.search;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.view.SectionHeaderViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.ExpandViewHolder;

public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_SECTION_HEADER = Model.TYPE_FIRST;
    public static final int VIEW_TYPE_EXPAND_ACTION = VIEW_TYPE_SECTION_HEADER + 1;


    class Section extends RecyclerView.AdapterDataObserver {
        private final long id;
        private final String title;
        private final boolean expandable;
        private final BaseAdapter adapter;
        private String expandText;

        public Section(long id, String title, boolean expandable, BaseAdapter adapter) {
            this.id = id;
            this.title = title;
            this.expandable = expandable;
            this.adapter = adapter;
        }

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }
    }

    private final LayoutInflater layoutInflater;
    private final SparseArray<Section> sectionsHeaders = new SparseArray<>();
    private final SparseArray<Section> expandActions = new SparseArray<>();
    private final List<Pair<Integer, Integer>> sectionRanges = new ArrayList<>();
    private final List<Section> sections = new ArrayList<>();
    private final SparseArray<Integer> viewTypeMap = new SparseArray<>();

    private int itemCount = 0;

    @Inject
    public SectionAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SECTION_HEADER) {
            View view = layoutInflater.inflate(R.layout.list_item_section_header, parent, false);
            return new SectionHeaderViewHolder(view);
        }

        if(viewType == VIEW_TYPE_EXPAND_ACTION) {
            View view = layoutInflater.inflate(R.layout.list_item_expand, parent, false);
            return new ExpandViewHolder(view);
        }

        return sections.get(viewTypeMap.get(viewType)).adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == VIEW_TYPE_SECTION_HEADER) {
            Section section = sectionsHeaders.get(position);
            ((SectionHeaderViewHolder)holder).setTitle(section.title);
        } else if(holder.getItemViewType() == VIEW_TYPE_EXPAND_ACTION) {
            Section section = expandActions.get(position);
            ((ExpandViewHolder)holder).setText(section.expandText);
        } else {
            int sectionIndex = getSectionForPosition(position);
            Section section = sections.get(sectionIndex);
            int sectionPosition = mapToSectionPosition(sectionIndex, position);
            section.adapter.onBindViewHolder(holder, sectionPosition);
        }

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    private void updateSections() {
        sectionRanges.clear();;
        sectionsHeaders.clear();
        expandActions.clear();
        viewTypeMap.clear();
        itemCount = 0;
        int offset = 0;
        for(int i = 0; i < sections.size(); ++i) {
            Section section = sections.get(i);
            sectionsHeaders.put(offset, section);
            offset++;
            sectionRanges.add(new Pair<>(offset, offset + section.adapter.getItemCount()));
            offset += section.adapter.getItemCount();
            if(section.expandable) {
                expandActions.put(offset, section);
                offset++;
            }
        }
        itemCount = offset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(sectionsHeaders.get(position) != null) {
            return VIEW_TYPE_SECTION_HEADER;
        }

        if(expandActions.get(position) != null) {
            return VIEW_TYPE_EXPAND_ACTION;
        }

        int sectionIndex = getSectionForPosition(position);
        Section section = sections.get(sectionIndex);
        int sectionPosition = mapToSectionPosition(sectionIndex, position);
        int viewType = section.adapter.getItemViewType(sectionPosition);
        viewTypeMap.put(viewType, sectionIndex);
        return viewType;
    }

    private int mapToSectionPosition(int section, int position) {
        return position-sectionRanges.get(section).first;
    }

    private int getSectionForPosition(int position) {
        for(int i = 0; i < sections.size(); ++i) {
            Pair<Integer, Integer> range = sectionRanges.get(i);
            if(position >= range.first && position < range.second) {
                return i;
            }
        }

        return -1;
    }

    public void reset() {
        sectionsHeaders.clear();
        expandActions.clear();
        sectionRanges.clear();
        sections.clear();
        viewTypeMap.clear();
        itemCount = 0;
        notifyDataSetChanged();
    }

    public void addSection(long id, String title, BaseAdapter adapter) {
        Section section = new Section(id, title, false, adapter);
        addSection(section);
    }

    public void addExpandableSection(long id, String title, String expandText, BaseAdapter adapter) {
        Section section = new Section(id, title, true, adapter);
        section.expandText = expandText;
        addSection(section);
    }

    public long getSectionId(int position) {
        Section section = sectionsHeaders.get(position);
        if(section == null) {
            section = expandActions.get(position);
        }

        if(section == null) {
            int sectionPosition = getSectionForPosition(position);
            section = sections.get(sectionPosition);
        }

        return section.id;
    }

    protected void addSection(Section section) {
        sections.add(section);
        sectionsHeaders.put(itemCount, section);
        section.adapter.registerAdapterDataObserver(section);
        updateSections();
    }

    public <T> T getItem(int position) {
        if(sectionsHeaders.get(position) != null) {
            return null;
        }

        if(expandActions.get(position) != null) {
            return null;
        }

        int sectionIndex = getSectionForPosition(position);
        Section section = sections.get(sectionIndex);
        return (T)section.adapter.getItem(mapToSectionPosition(sectionIndex, position));
    }
}
