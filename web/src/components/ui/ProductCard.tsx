import React from 'react';

export interface ProductItem {
  id: number;
  name: string;
  basePrice: number;
  mrp: number;
  mainImageUrl: string;
  rating: number;
  reviewCount: number;
  categoryName: string;
  sellerBusinessName?: string;
  isFlashSale?: boolean;
}

interface ProductCardProps {
  product: ProductItem;
  onAddToCart: (product: ProductItem) => void;
  onQuickView: (product: ProductItem) => void;
}

export const ProductCard: React.FC<ProductCardProps> = ({
  product,
  onAddToCart,
  onQuickView,
}) => {
  const discountPercent = Math.round(((product.mrp - product.basePrice) / product.mrp) * 100);

  return (
    <div className="bg-white rounded-2xl border border-gray-100 shadow-card hover:shadow-hover transition-all duration-300 overflow-hidden flex flex-col group relative">
      {/* Image & Badges */}
      <div className="relative overflow-hidden bg-gray-50 h-52 flex items-center justify-center p-4">
        {discountPercent > 0 && (
          <span className="absolute top-3 left-3 bg-gradient-to-r from-red-500 to-rose-600 text-white font-extrabold text-[11px] px-2.5 py-1 rounded-full shadow-md z-10">
            {discountPercent}% OFF
          </span>
        )}
        {product.isFlashSale && (
          <span className="absolute top-3 right-3 bg-accent-500 text-surface-dark font-extrabold text-[10px] uppercase px-2 py-0.5 rounded shadow z-10">
            ⚡ Flash Deal
          </span>
        )}

        <img
          src={product.mainImageUrl}
          alt={product.name}
          className="h-full w-full object-contain group-hover:scale-105 transition-transform duration-300"
        />

        {/* Quick Action Overlay */}
        <div className="absolute inset-0 bg-black/20 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center space-x-2">
          <button
            onClick={() => onQuickView(product)}
            className="bg-white text-surface-dark font-semibold text-xs px-3 py-2 rounded-full shadow hover:bg-brand-500 hover:text-white transition"
          >
            Quick View 👁️
          </button>
        </div>
      </div>

      {/* Product Details */}
      <div className="p-4 flex-1 flex flex-col justify-between">
        <div>
          <div className="flex items-center justify-between text-xs mb-1">
            <span className="text-brand-500 font-bold uppercase tracking-wider text-[10px]">
              {product.categoryName}
            </span>
            {product.sellerBusinessName && (
              <span className="text-gray-400 text-[10px] truncate max-w-[120px]">
                by {product.sellerBusinessName}
              </span>
            )}
          </div>

          <h3 className="font-bold text-gray-900 text-sm line-clamp-2 hover:text-brand-500 transition cursor-pointer mb-2">
            {product.name}
          </h3>

          {/* Rating */}
          <div className="flex items-center space-x-1 mb-3">
            <div className="flex text-amber-400 text-xs">
              {'★'.repeat(Math.floor(product.rating))}
              {'☆'.repeat(5 - Math.floor(product.rating))}
            </div>
            <span className="text-xs font-bold text-gray-700">{product.rating}</span>
            <span className="text-xs text-gray-400">({product.reviewCount})</span>
          </div>
        </div>

        <div>
          {/* Price */}
          <div className="flex items-baseline space-x-2 mb-3">
            <span className="text-lg font-black text-brand-600">
              ₹{product.basePrice.toLocaleString('en-IN')}
            </span>
            <span className="text-xs text-gray-400 line-through">
              ₹{product.mrp.toLocaleString('en-IN')}
            </span>
          </div>

          {/* Add to Cart Button */}
          <button
            onClick={() => onAddToCart(product)}
            className="w-full bg-brand-500 text-white font-bold py-2.5 px-4 rounded-xl text-xs hover:bg-brand-600 active:scale-[0.98] transition shadow-md shadow-brand-500/20 flex items-center justify-center space-x-2"
          >
            <span>🛒</span>
            <span>Add to Cart</span>
          </button>
        </div>
      </div>
    </div>
  );
};
