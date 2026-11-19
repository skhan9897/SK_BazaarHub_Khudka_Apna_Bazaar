package com.skbazaarhub.web.components.ui;

import React from 'react';

export const Footer: React.FC = () => {
  return (
    <footer className="bg-surface-dark text-gray-400 text-sm border-t border-gray-800 pt-12 pb-8 mt-16">
      <div className="max-w-7xl mx-auto px-4 grid grid-cols-1 md:grid-cols-4 gap-8 mb-12">
        {/* Col 1: Brand Info */}
        <div>
          <div className="flex items-center space-x-2 mb-4">
            <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-brand-500 to-brand-700 flex items-center justify-center text-white font-black text-lg">
              SK
            </div>
            <span className="text-xl font-black text-white">
              SK<span className="text-brand-500">Bazaar</span><span className="text-accent-500">Hub</span>
            </span>
          </div>
          <p className="text-gray-400 leading-relaxed mb-4">
            India's premiere multi-vendor e-commerce platform powering thousands of independent sellers and delivering quality products nationwide.
          </p>
          <div className="flex space-x-3 text-lg">
            <span className="cursor-pointer hover:text-white">🌐</span>
            <span className="cursor-pointer hover:text-white">📱</span>
            <span className="cursor-pointer hover:text-white">💬</span>
          </div>
        </div>

        {/* Col 2: Quick Links */}
        <div>
          <h4 className="text-white font-bold text-base mb-4">Shop & Explore</h4>
          <ul className="space-y-2.5">
            <li><a href="#" className="hover:text-white transition">Electronics & Gadgets</a></li>
            <li><a href="#" className="hover:text-white transition">Fashion & Apparel</a></li>
            <li><a href="#" className="hover:text-white transition">Home Essentials</a></li>
            <li><a href="#" className="hover:text-white transition">Flash Sale & Deals</a></li>
            <li><a href="#" className="hover:text-white transition">Trending Brands</a></li>
          </ul>
        </div>

        {/* Col 3: Customer Care */}
        <div>
          <h4 className="text-white font-bold text-base mb-4">Customer Support</h4>
          <ul className="space-y-2.5">
            <li><a href="#" className="hover:text-white transition">My Account & Orders</a></li>
            <li><a href="#" className="hover:text-white transition">Shipment Tracking</a></li>
            <li><a href="#" className="hover:text-white transition">Returns & Refunds Policy</a></li>
            <li><a href="#" className="hover:text-white transition">Help Center & FAQ</a></li>
            <li><a href="#" className="hover:text-white transition">Become a Verified Seller</a></li>
          </ul>
        </div>

        {/* Col 4: Newsletter */}
        <div>
          <h4 className="text-white font-bold text-base mb-4">Stay Updated</h4>
          <p className="text-xs text-gray-400 mb-3">Subscribe for exclusive flash sale updates & coupons.</p>
          <div className="flex mb-4">
            <input
              type="email"
              placeholder="Enter your email"
              className="bg-gray-800 border border-gray-700 text-white text-xs px-3 py-2 rounded-l-lg focus:outline-none w-full"
            />
            <button className="bg-brand-500 text-white font-bold px-4 py-2 text-xs rounded-r-lg hover:bg-brand-600 transition">
              Join
            </button>
          </div>
          <div className="text-xs text-gray-500">
            🔒 100% Secure Payments with Razorpay, UPI & NetBanking
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 border-t border-gray-800 pt-6 flex flex-col md:flex-row justify-between items-center text-xs text-gray-500">
        <p>© 2026 SK BazaarHub Platform. Powered by Unified Microservices Architecture.</p>
        <div className="flex space-x-6 mt-4 md:mt-0">
          <a href="#" className="hover:text-gray-400">Privacy Policy</a>
          <a href="#" className="hover:text-gray-400">Terms of Service</a>
          <a href="#" className="hover:text-gray-400">Seller Agreement</a>
        </div>
      </div>
    </footer>
  );
};
