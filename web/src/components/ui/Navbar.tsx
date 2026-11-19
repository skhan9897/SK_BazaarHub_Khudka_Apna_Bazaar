import React, { useState } from 'react';

interface NavbarProps {
  activePortal: 'customer' | 'seller' | 'admin';
  setActivePortal: (portal: 'customer' | 'seller' | 'admin') => void;
  cartCount: number;
  wishlistCount: number;
  onCartClick: () => void;
  onSearch: (query: String) => void;
}

export const Navbar: React.FC<NavbarProps> = ({
  activePortal,
  setActivePortal,
  cartCount,
  wishlistCount,
  onCartClick,
  onSearch,
}) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [showCategoryDrawer, setShowCategoryDrawer] = useState(false);

  const categories = [
    { name: 'Electronics', icon: '⚡' },
    { name: 'Fashion', icon: '👕' },
    { name: 'Mobiles', icon: '📱' },
    { name: 'Grocery', icon: '🛒' },
    { name: 'Home & Kitchen', icon: '🏠' },
    { name: 'Beauty', icon: '✨' },
    { name: 'Appliances', icon: '🔌' },
    { name: 'Sports', icon: '⚽' },
  ];

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(searchQuery);
  };

  return (
    <header className="sticky top-0 z-50 bg-white border-b border-gray-100 shadow-sm">
      {/* 1. Announcement Bar */}
      <div className="bg-gradient-to-r from-brand-900 via-brand-700 to-brand-500 text-white text-xs py-2 px-4">
        <div className="max-w-7xl mx-auto flex justify-between items-center">
          <div className="flex items-center space-x-4">
            <span className="bg-accent-500 text-surface-dark font-bold px-2 py-0.5 rounded text-[10px] uppercase tracking-wide">
              Flash Deal
            </span>
            <span className="hidden md:inline font-medium">
              Free Express Shipping on Orders Above ₹499! Code: <strong className="text-accent-400">SKFREESHIP</strong>
            </span>
          </div>
          <div className="flex items-center space-x-6 text-gray-200">
            <a href="#support" className="hover:text-white transition">24/7 Support</a>
            <span className="text-gray-400">|</span>
            <button
              onClick={() => setActivePortal('seller')}
              className="font-semibold text-accent-400 hover:underline"
            >
              Become a Seller 🚀
            </button>
          </div>
        </div>
      </div>

      {/* 2. Main Navigation Bar */}
      <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between gap-4">
        {/* Brand Logo & Mobile Menu Toggle */}
        <div className="flex items-center space-x-3">
          <button
            onClick={() => setShowCategoryDrawer(!showCategoryDrawer)}
            className="md:hidden p-2 rounded-lg text-gray-600 hover:bg-gray-100"
          >
            ☰
          </button>
          <div
            onClick={() => setActivePortal('customer')}
            className="cursor-pointer flex items-center space-x-2"
          >
            <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-brand-500 to-brand-700 flex items-center justify-center text-white font-black text-xl shadow-md">
              SK
            </div>
            <div>
              <span className="text-2xl font-extrabold tracking-tight text-surface-dark">
                SK<span className="text-brand-500">Bazaar</span><span className="text-accent-500">Hub</span>
              </span>
              <span className="block text-[9px] uppercase font-extrabold text-brand-600 tracking-wider -mt-1">
                KHUDKA APNA BAZAAR • DEVELOPED BY SAJID KHAN
              </span>
            </div>
          </div>
        </div>

        {/* Global Search Bar */}
        <form onSubmit={handleSearchSubmit} className="flex-1 max-w-2xl hidden md:flex relative">
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search 50,000+ products, categories or brands..."
            className="w-full bg-surface-bg border border-gray-200 rounded-full py-2.5 pl-5 pr-12 text-sm text-surface-dark placeholder-gray-400 focus:outline-none focus:border-brand-500 focus:bg-white focus:ring-2 focus:ring-brand-500/20 transition shadow-inner"
          />
          <button
            type="submit"
            className="absolute right-1.5 top-1.5 bottom-1.5 px-4 bg-brand-500 text-white rounded-full font-medium hover:bg-brand-600 transition flex items-center justify-center"
          >
            🔍
          </button>
        </form>

        {/* Actions & Portal Tabs */}
        <div className="flex items-center space-x-5">
          {/* Portal Switcher Pill */}
          <div className="hidden lg:flex bg-surface-bg p-1 rounded-full border border-gray-200">
            <button
              onClick={() => setActivePortal('customer')}
              className={`px-3 py-1 rounded-full text-xs font-semibold transition ${
                activePortal === 'customer'
                  ? 'bg-brand-500 text-white shadow-sm'
                  : 'text-gray-600 hover:text-gray-900'
              }`}
            >
              Shop
            </button>
            <button
              onClick={() => setActivePortal('seller')}
              className={`px-3 py-1 rounded-full text-xs font-semibold transition ${
                activePortal === 'seller'
                  ? 'bg-brand-500 text-white shadow-sm'
                  : 'text-gray-600 hover:text-gray-900'
              }`}
            >
              Seller
            </button>
            <button
              onClick={() => setActivePortal('admin')}
              className={`px-3 py-1 rounded-full text-xs font-semibold transition ${
                activePortal === 'admin'
                  ? 'bg-brand-500 text-white shadow-sm'
                  : 'text-gray-600 hover:text-gray-900'
              }`}
            >
              Admin
            </button>
          </div>

          {/* Wishlist Button */}
          <button className="relative p-2 text-gray-700 hover:text-brand-500 transition">
            <span className="text-xl">❤️</span>
            {wishlistCount > 0 && (
              <span className="absolute -top-1 -right-1 bg-accent-500 text-surface-dark font-extrabold text-[10px] w-5 h-5 rounded-full flex items-center justify-center border-2 border-white">
                {wishlistCount}
              </span>
            )}
          </button>

          {/* Cart Drawer Trigger */}
          <button
            onClick={onCartClick}
            className="relative bg-brand-50 border border-brand-100 hover:bg-brand-100 text-brand-600 px-4 py-2 rounded-full font-semibold text-sm transition flex items-center space-x-2"
          >
            <span>🛒</span>
            <span>Cart</span>
            {cartCount > 0 && (
              <span className="bg-brand-500 text-white font-extrabold text-xs px-2 py-0.5 rounded-full shadow">
                {cartCount}
              </span>
            )}
          </button>
        </div>
      </div>

      {/* 3. Category Navigation Ribbon */}
      <div className="border-t border-gray-100 bg-surface-bg hidden md:block">
        <div className="max-w-7xl mx-auto px-4 py-2 flex space-x-8 overflow-x-auto text-sm font-medium text-gray-700">
          {categories.map((cat, idx) => (
            <button
              key={idx}
              className="flex items-center space-x-1.5 hover:text-brand-500 transition whitespace-nowrap py-1"
            >
              <span>{cat.icon}</span>
              <span>{cat.name}</span>
            </button>
          ))}
        </div>
      </div>
    </header>
  );
};
