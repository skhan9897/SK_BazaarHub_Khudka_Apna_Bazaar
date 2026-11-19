import React, { useState } from 'react';
import { ProductCard, ProductItem } from '../../components/ui/ProductCard';

interface ProductListingProps {
  products: ProductItem[];
  onAddToCart: (product: ProductItem) => void;
  onSelectProduct: (product: ProductItem) => void;
}

export const ProductListing: React.FC<ProductListingProps> = ({
  products,
  onAddToCart,
  onSelectProduct,
}) => {
  const [selectedCategory, setSelectedCategory] = useState<string>('All');
  const [maxPrice, setMaxPrice] = useState<number>(50000);
  const [sortBy, setSortBy] = useState<string>('relevance');

  const filteredProducts = products.filter((p) => {
    const matchesCategory = selectedCategory === 'All' || p.categoryName === selectedCategory;
    const matchesPrice = p.basePrice <= maxPrice;
    return matchesCategory && matchesPrice;
  });

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
        <div>
          <h1 className="text-3xl font-black text-gray-900 tracking-tight">Catalog Search Results</h1>
          <p className="text-gray-500 text-sm mt-1">Showing {filteredProducts.length} verified items</p>
        </div>

        {/* Sorting Dropdown */}
        <div className="flex items-center space-x-3">
          <label className="text-xs font-bold text-gray-500 uppercase tracking-wider">Sort By:</label>
          <select
            value={sortBy}
            onChange={(e) => setSortBy(e.target.value)}
            className="bg-white border border-gray-200 rounded-xl px-4 py-2 text-sm font-semibold text-gray-700 focus:outline-none focus:border-brand-500 shadow-sm"
          >
            <option value="relevance">Relevance</option>
            <option value="price-low">Price: Low to High</option>
            <option value="price-high">Price: High to Low</option>
            <option value="rating">Highest Rated</option>
          </select>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
        {/* Filter Sidebar */}
        <div className="bg-white p-6 rounded-2xl border border-gray-100 shadow-card h-fit space-y-6">
          <h3 className="font-extrabold text-base text-gray-900 border-b pb-3">Filters</h3>

          {/* Category Filter */}
          <div>
            <h4 className="font-bold text-xs uppercase text-gray-400 tracking-wider mb-3">Categories</h4>
            <div className="space-y-2 text-sm">
              {['All', 'Electronics', 'Wearables', 'Furniture', 'Audio'].map((cat) => (
                <label key={cat} className="flex items-center space-x-2 cursor-pointer">
                  <input
                    type="radio"
                    name="category"
                    checked={selectedCategory === cat}
                    onChange={() => setSelectedCategory(cat)}
                    className="text-brand-500 focus:ring-brand-500"
                  />
                  <span className={selectedCategory === cat ? 'font-bold text-brand-600' : 'text-gray-700'}>
                    {cat}
                  </span>
                </label>
              ))}
            </div>
          </div>

          {/* Price Range Slider */}
          <div>
            <h4 className="font-bold text-xs uppercase text-gray-400 tracking-wider mb-3">Max Price: ₹{maxPrice.toLocaleString('en-IN')}</h4>
            <input
              type="range"
              min="1000"
              max="50000"
              step="1000"
              value={maxPrice}
              onChange={(e) => setMaxPrice(Number(e.target.value))}
              className="w-full accent-brand-500 cursor-pointer"
            />
          </div>
        </div>

        {/* Product Grid */}
        <div className="md:col-span-3">
          {filteredProducts.length === 0 ? (
            <div className="bg-white rounded-2xl p-12 text-center border border-gray-100 shadow-sm">
              <span className="text-4xl mb-3 block">🔍</span>
              <h3 className="font-bold text-lg text-gray-800">No products match your filters</h3>
              <p className="text-xs text-gray-500 mt-1">Try expanding your price range or selecting another category.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
              {filteredProducts.map((product) => (
                <ProductCard
                  key={product.id}
                  product={product}
                  onAddToCart={onAddToCart}
                  onQuickView={onSelectProduct}
                />
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
