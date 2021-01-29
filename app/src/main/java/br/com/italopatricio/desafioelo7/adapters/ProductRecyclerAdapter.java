package br.com.italopatricio.desafioelo7.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.italopatricio.desafioelo7.R;
import br.com.italopatricio.desafioelo7.activities.WebViewActivity;
import br.com.italopatricio.desafioelo7.helpers.PictureHelper;
import br.com.italopatricio.desafioelo7.models.ProductModel;
import br.com.italopatricio.desafioelo7.models.ProductResultModel;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private static final String TAG = "ProductRecyclerAdapter";
    private final Context context;
    private ProductResultModel productResultModel;

    public ProductRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card_item, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element" + position + " set.");
        holder.setValues(productResultModel.getProductModelList().get(position));
    }

    @Override
    public int getItemCount() {
        return productResultModel.getProductModelList().size();
    }

    public String getResults() {
        return  String.format("%s produtos encontrados",productResultModel.getProductModelList().size());
    }

    public void addProductResultModel(ProductResultModel productResultModel) {
        this.productResultModel.getProductModelList().addAll(productResultModel.getProductModelList());
    }

    public void setProductRecyclerAdapter(ProductResultModel productResultModel) {
        this.productResultModel = productResultModel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ProductModel productModel;

        private final View cardView;
        private final ImageView pictureView;
        private final TextView titleView;
        private final TextView nonPromotionView;
        private final TextView priceView;
        private final TextView installmentView;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            pictureView = itemView.findViewById(R.id.card_image_view);
            titleView = itemView.findViewById(R.id.txt_description);
            nonPromotionView = itemView.findViewById(R.id.txt_price_old);
            priceView = itemView.findViewById(R.id.txt_price);
            installmentView = itemView.findViewById(R.id.txt_price_installment);
            cardView = itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, productModel.getLink());
                context.startActivity(intent);
            });
        }

        public void setValues(ProductModel productModel) {
            this.productModel = productModel;

            if (productModel.getTitle() != null) {
                titleView.setText(productModel.getTitle());
            }

            if (productModel.getPrice().getNonPromotion() != null) {
                nonPromotionView.setText(productModel.getPrice().getNonPromotion());
            }

            priceView.setText(productModel.getPrice().getCurrent());

            if (productModel.getPrice().getInstallment() != null) {
                installmentView.setText(productModel.getPrice().getInstallment());
            }

            PictureHelper.downloadImageUrl(productModel.getPicture())
                    .error(R.drawable.kiy_chocolate_baby_poster)
                    .fit()
                    .into(pictureView);
        }
    }
}
