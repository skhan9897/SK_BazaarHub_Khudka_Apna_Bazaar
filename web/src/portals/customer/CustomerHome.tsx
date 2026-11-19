import React, { useState } from 'react';
import { ProductCard, ProductItem } from '../../components/ui/ProductCard';

interface CustomerHomeProps {
  onAddToCart: (product: ProductItem) => void;
  onSelectProduct: (product: ProductItem) => void;
}

export const CustomerHome: React.FC<CustomerHomeProps> = ({
  onAddToCart,
  onSelectProduct,
}) => {
  const [activeSlide, setActiveSlide] = useState(0);

  const heroBanners = [
    {
      id: 1,
      tag: "BIG DIWALI DHAMAKA",
      title: "Up to 80% Off Tech & Appliances",
      subtitle: "Shop top brands with Instant Bank Discounts & No Cost EMI",
      buttonText: "Explore Tech Deals 🚀",
      bgGradient: "from-brand-900 via-brand-700 to-brand-500",
      imageUrl: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=800"
    },
    {
      id: 2,
      tag: "FASHION FESTIVAL",
      title: "Trendy Festive Collections 2026",
      subtitle: "Discover ethnic wear, western trends & luxury watches",
      buttonText: "Shop Fashion 👕",
      bgGradient: "from-indigo-900 via-purple-800 to-pink-600",
      imageUrl: "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=800"
    }
  ];

  const featuredProducts: ProductItem[] = [
    {
      id: 101,
      name: "Sony WH-1000XM5 Wireless Noise Cancelling Headphones",
      basePrice: 24990,
      mrp: 34990,
      mainImageUrl: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500",
      rating: 4.8,
      reviewCount: 1420,
      categoryName: "Electronics",
      sellerBusinessName: "Sony India Official",
      isFlashSale: true
    },
    {
      id: 102,
      name: "Apple Watch Series 9 GPS 45mm Midnight Aluminum",
      basePrice: 41900,
      mrp: 44900,
      mainImageUrl: "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500",
      rating: 4.9,
      reviewCount: 980,
      categoryName: "Wearables",
      sellerBusinessName: "Apple Retailer Hub"
    },
    {
      id: 103,
      name: "Ergonomic High-Back Executive Mesh Office Chair",
      basePrice: 8499,
      mrp: 16999,
      mainImageUrl: "https://images.unsplash.com/photo-1580481072645-022f9a6d128d?w=500",
      rating: 4.6,
      reviewCount: 310,
      categoryName: "Furniture",
      sellerBusinessName: "GreenSoul Comfort",
      isFlashSale: true
    },
    {
      id: 104,
      name: "Bose SoundLink Flex Portable Bluetooth Speaker",
      basePrice: 12900,
      mrp: 15900,
      mainImageUrl: "https://images.unsplash.com/photo-1545454675-3531b543be5d?w=500",
      rating: 4.7,
      reviewCount: 520,
      categoryName: "Audio",
      sellerBusinessName: "Bose Official"
    }
  ];

  const brandLogos = [
    { name: "Sony", logo: "⚡" },
    { name: "Apple", logo: "🍎" },
    { name: "Samsung", logo: "🌌" },
    { name: "Nike", logo: "✔️" },
    { name: "LG", logo: "📺" },
    { name: "Bose", logo: "🎵" }
  ];

  return (
    <div className="space-y-10 py-6">
      {/* 1. Hero Promotional Campaign Slider */}
      <section className="max-w-7xl mx-auto px-4">
        <div className={`relative rounded-3xl p-8 md:p-12 text-white bg-gradient-to-r ${heroBanners[activeSlide].bgGradient} shadow-2xl overflow-hidden min-h-[320px] flex items-center`}>
          <div className="max-w-xl z-10 space-y-4">
            <span className="inline-block bg-accent-500 text-surface-dark font-extrabold text-xs uppercase px-3 py-1 rounded-full tracking-wider shadow">
              {heroBanners[activeSlide].tag}
            </span>
            <h1 className="text-3xl md:text-5xl font-black leading-tight tracking-tight">
              {heroBanners[activeSlide].title}
            </h1>
            <p className="text-sm md:text-base opacity-90 leading-relaxed">
              {heroBanners[activeSlide].subtitle}
            </p>
            <button className="bg-white text-brand-900 font-extrabold px-6 py-3 rounded-full hover:bg-accent-400 hover:text-surface-dark transition shadow-lg active:scale-95 text-sm">
              {heroBanners[activeSlide].buttonText}
            </button>
          </div>

          <img
            src={heroBanners[activeSlide].imageUrl}
            alt="Hero Banner"
            className="absolute right-0 top-0 bottom-0 h-full w-1/2 object-cover opacity-30 md:opacity-80 mix-blend-overlay hidden md:block"
          />

          {/* Slider Controls */}
          <div className="absolute bottom-4 right-8 flex space-x-2">
            {heroBanners.map((_, idx) => (
              <button
                key={idx}
                onClick={() => setActiveSlide(idx)}
                className={`w-3 h-3 rounded-full transition ${
                  activeSlide === idx ? 'bg-accent-500 w-8' : 'bg-white/40'
                }`}
              />
            ))}
          </div>
        </div>
      </section>

      {/* 2. Top Brands Ribbon */}
      <section className="max-w-7xl mx-auto px-4">
        <div className="bg-white rounded-2xl p-6 border border-gray-100 shadow-card flex items-center justify-between overflow-x-auto gap-6">
          <span className="font-extrabold text-xs uppercase tracking-widest text-gray-400 whitespace-nowrap">
            Official Brand Partners:
          </span>
          {brandLogos.map((brand, idx) => (
            <div key={idx} className="flex items-center space-x-2 font-bold text-gray-700 text-sm hover:text-brand-500 cursor-pointer whitespace-nowrap">
              <span className="text-xl">{brand.logo}</span>
              <span>{brand.name}</span>
            </div>
          ))}
        </div>
      </section>

      {/* 3. Flash Sale Section */}
      <section className="max-w-7xl mx-auto px-4">
        <div className="flex justify-between items-center mb-6">
          <div className="flex items-center space-x-3">
            <span className="text-2xl">⚡</span>
            <h2 className="text-2xl font-black text-gray-900 tracking-tight">Flash Deals</h2>
            <span className="bg-red-100 text-red-700 font-bold text-xs px-3 py-1 rounded-full animate-pulse">
              Ends in 04h 22m 15s
            </span>
          </div>
          <button className="text-brand-500 font-bold text-sm hover:underline">View All Deals →</button>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6">
          {featuredProducts.map((product) => (
            <ProductCard
              key={product.id}
              product={product}
              onAddToCart={onAddToCart}
              onQuickView={onSelectProduct}
            />
          ))}
        </div>
      </section>
    </div>
  );
};
