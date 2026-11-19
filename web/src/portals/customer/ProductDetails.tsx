import React, { useState } from 'react';
import { ProductItem } from '../../components/ui/ProductCard';

interface ProductDetailsProps {
  product: ProductItem;
  onAddToCart: (product: ProductItem) => void;
  onBack: () => void;
}

export const ProductDetails: React.FC<ProductDetailsProps> = ({
  product,
  onAddToCart,
  onBack,
}) => {
  const [selectedColor, setSelectedColor] = useState('Midnight Black');
  const [pincode, setPincode] = useState('');
  const [pincodeStatus, setPincodeStatus] = useState<string | null>(null);

  const handleCheckPincode = (e: React.FormEvent) => {
    e.preventDefault();
    if (pincode.length === 6) {
      setPincodeStatus('✅ Delivery Available in 2 Business Days! Free Express Delivery.');
    } else {
      setPincodeStatus('❌ Invalid Pincode. Enter a 6-digit Indian PIN Code.');
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Back Button */}
      <button
        onClick={onBack}
        className="mb-6 text-sm font-bold text-gray-500 hover:text-brand-500 transition flex items-center space-x-1"
      >
        <span>←</span>
        <span>Back to Catalog</span>
      </button>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 bg-white p-8 rounded-3xl border border-gray-100 shadow-card">
        {/* Gallery Image */}
        <div className="bg-gray-50 rounded-2xl p-8 flex items-center justify-center border border-gray-100 h-[420px]">
          <img
            src={product.mainImageUrl}
            alt={product.name}
            className="max-h-full max-w-full object-contain"
          />
        </div>

        {/* Info & Purchase Selector */}
        <div className="space-y-6">
          <div>
            <span className="bg-brand-50 text-brand-600 font-extrabold text-xs px-3 py-1 rounded-full uppercase tracking-wider">
              {product.categoryName}
            </span>
            <h1 className="text-2xl md:text-3xl font-black text-gray-900 mt-2 leading-tight">
              {product.name}
            </h1>
            <p className="text-xs text-gray-400 mt-1">
              Sold & Fulfilled by <strong className="text-gray-700">{product.sellerBusinessName || 'SK BazaarHub Verified Merchant'}</strong>
            </p>
          </div>

          {/* Rating */}
          <div className="flex items-center space-x-2">
            <div className="flex text-amber-400 text-sm">
              {'★'.repeat(Math.floor(product.rating))}
            </div>
            <span className="font-bold text-sm text-gray-800">{product.rating}</span>
            <span className="text-xs text-gray-400">({product.reviewCount} customer reviews)</span>
          </div>

          {/* Pricing */}
          <div className="flex items-baseline space-x-3 bg-surface-bg p-4 rounded-2xl border">
            <span className="text-3xl font-black text-brand-600">
              ₹{product.basePrice.toLocaleString('en-IN')}
            </span>
            <span className="text-sm text-gray-400 line-through">
              ₹{product.mrp.toLocaleString('en-IN')}
            </span>
            <span className="bg-red-100 text-red-700 font-bold text-xs px-2.5 py-1 rounded-full">
              Save ₹{(product.mrp - product.basePrice).toLocaleString('en-IN')}
            </span>
          </div>

          {/* Pincode Checker */}
          <form onSubmit={handleCheckPincode} className="space-y-2">
            <label className="block text-xs font-bold uppercase text-gray-400 tracking-wider">
              Delivery Availability
            </label>
            <div className="flex max-w-xs">
              <input
                type="text"
                placeholder="Enter 6-digit PIN code"
                value={pincode}
                onChange={(e) => setPincode(e.target.value)}
                maxLength={6}
                className="border border-gray-200 rounded-l-xl px-3 py-2 text-xs font-medium focus:outline-none focus:border-brand-500 w-full"
              />
              <button
                type="submit"
                className="bg-brand-500 text-white font-bold text-xs px-4 rounded-r-xl hover:bg-brand-600 transition"
              >
                Check
              </button>
            </div>
            {pincodeStatus && (
              <p className={`text-xs font-medium mt-1 ${pincodeStatus.startsWith('✅') ? 'text-green-600' : 'text-red-500'}`}>
                {pincodeStatus}
              </p>
            )}
          </form>

          {/* Action Buttons */}
          <div className="flex space-x-4 pt-4">
            <button
              onClick={() => onAddToCart(product)}
              className="flex-1 bg-brand-500 text-white font-bold py-3.5 rounded-xl hover:bg-brand-600 transition shadow-lg shadow-brand-500/20 text-sm active:scale-[0.98]"
            >
              Add to Cart 🛒
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
