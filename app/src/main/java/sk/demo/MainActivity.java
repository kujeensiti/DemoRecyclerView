package sk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG_LOG = "MainActivity";

    private RecyclerView rvCells;

    private static class Cell {

        Cell(String title, String text) {
            this.title = title;
            this.text = text;
        }

        private String title, text;
    }

    private ArrayList<Cell> cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cells = new ArrayList<>();

        // create 1000 cards with dummy title and message
        String text = getString(R.string.lorem);
        String title;

        for (int i = 1; i <= 1000; i++) {

            // title to show post id (starting from 1) and whether it is with/without image
            title = "" + i + ". Sample Title For Cell";
            cells.add(new Cell(title, text));
        }

        rvCells = (RecyclerView) findViewById(R.id.rv__act_main__post_list);

        rvCells.setVisibility(View.VISIBLE);
        rvCells.setLayoutManager(new LinearLayoutManager(this));
        rvCells.setAdapter(new PostsAdapter(cells));

    }

    private class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.CellViewHolder> {

        private int $cellViewHolderCounter;

        private List<Cell> data;

        PostsAdapter(List<Cell> data) {
            this.data = data;

            // we will use these counters to keep track of allocations of ViewHolders
            $cellViewHolderCounter = 0;
        }

        @Override
        public CellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
            int $viewHolderId = $cellViewHolderCounter++;
            return new CellViewHolder(itemView, $viewHolderId);
        }

        @Override
        public void onBindViewHolder(CellViewHolder holder, int position) {
            holder.onBindViewHolder(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private int $viewHolderId;

            private int position;
            private TextView tvTitle, tvText;
            private Button btnReadMore;

            CellViewHolder(View itemView, int $viewHolderId) {
                super(itemView);
                this.$viewHolderId = $viewHolderId;
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                tvText = (TextView) itemView.findViewById(R.id.tv_text);
                btnReadMore = (Button) itemView.findViewById(R.id.btn_read_more);
                btnReadMore.setOnClickListener(this);
            }

            void onBindViewHolder(int position) {
                this.position = position;
                Cell cell = data.get(position);
                tvTitle.setText(cell.title);
                tvText.setText(cell.text);
                Log.d(TAG_LOG, "onBindViewHolder: ViewHolder " + $viewHolderId + " displaying cell " + position);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_read_more: {
                        Cell cell = data.get(position);
                        Toast.makeText(getApplicationContext(), cell.title, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }
    }
}

