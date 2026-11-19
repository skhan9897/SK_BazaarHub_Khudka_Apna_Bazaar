import React, { useState } from 'react';

export const CustomerAccount: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'profile' | 'orders' | 'addresses'>('orders');

  const orders = [
    {
      id: "SKB-173200984",
      date: "Nov 18, 2026",
      total: 24990,
      status: "SHIPPED",
      items: "Sony WH-1000XM5 Noise Cancelling Headphones",
      tracking: "TRK-987654321"
    },
    {
      id: "SKB-173195420",
      date: "Nov 12, 2026",
      total: 8499,
      status: "DELIVERED",
      items: "Ergonomic High-Back Office Chair",
      tracking: "TRK-123456789"
    }
  ];

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <div>
          <h1 className="text-3xl font-black text-gray-900 tracking-tight">My Account</h1>
          <p className="text-gray-500 text-sm">Manage profile, orders & addresses</p>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
        {/* Navigation Sidebar */}
        <div className="bg-white p-4 rounded-3xl border border-gray-100 shadow-card space-y-1 h-fit">
          <button
            onClick={() => setActiveTab('orders')}
            className={`w-full text-left px-4 py-3 rounded-2xl font-bold text-xs transition flex items-center space-x-2 ${
              activeTab === 'orders' ? 'bg-brand-500 text-white shadow' : 'text-gray-600 hover:bg-gray-50'
            }`}
          >
            <span>📦</span>
            <span>My Orders & Tracking</span>
          </button>
          <button
            onClick={() => setActiveTab('profile')}
            className={`w-full text-left px-4 py-3 rounded-2xl font-bold text-xs transition flex items-center space-x-2 ${
              activeTab === 'profile' ? 'bg-brand-500 text-white shadow' : 'text-gray-600 hover:bg-gray-50'
            }`}
          >
            <span>👤</span>
            <span>Profile & Security</span>
          </button>
          <button
            onClick={() => setActiveTab('addresses')}
            className={`w-full text-left px-4 py-3 rounded-2xl font-bold text-xs transition flex items-center space-x-2 ${
              activeTab === 'addresses' ? 'bg-brand-500 text-white shadow' : 'text-gray-600 hover:bg-gray-50'
            }`}
          >
            <span>📍</span>
            <span>Saved Addresses</span>
          </button>
        </div>

        {/* Content Area */}
        <div className="md:col-span-3 space-y-6">
          {activeTab === 'orders' && (
            <div className="space-y-4">
              <h2 className="font-extrabold text-xl text-gray-900">Order History</h2>
              {orders.map((order) => (
                <div key={order.id} className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card space-y-4">
                  <div className="flex justify-between items-center border-b pb-3 text-xs">
                    <div>
                      <span className="font-bold text-gray-900">{order.id}</span>
                      <span className="text-gray-400 ml-3">Placed on {order.date}</span>
                    </div>
                    <span className={`font-extrabold px-3 py-1 rounded-full text-[10px] ${
                      order.status === 'DELIVERED' ? 'bg-green-100 text-green-700' : 'bg-blue-100 text-blue-700'
                    }`}>
                      {order.status}
                    </span>
                  </div>

                  <div className="flex justify-between items-center">
                    <div>
                      <h4 className="font-bold text-sm text-gray-900">{order.items}</h4>
                      <p className="text-xs text-gray-400 mt-1">Tracking ID: {order.tracking}</p>
                    </div>
                    <span className="text-lg font-black text-brand-600">₹{order.total.toLocaleString('en-IN')}</span>
                  </div>
                </div>
              ))}
            </div>
          )}

          {activeTab === 'profile' && (
            <div className="bg-white p-8 rounded-3xl border border-gray-100 shadow-card space-y-4">
              <h2 className="font-extrabold text-xl text-gray-900">Profile Information</h2>
              <div className="grid grid-cols-2 gap-4 text-xs">
                <div>
                  <label className="block text-gray-400 font-bold mb-1">Name</label>
                  <p className="font-bold text-gray-900 text-sm">Rahul Sharma</p>
                </div>
                <div>
                  <label className="block text-gray-400 font-bold mb-1">Email</label>
                  <p className="font-bold text-gray-900 text-sm">rahul.sharma@example.com</p>
                </div>
                <div>
                  <label className="block text-gray-400 font-bold mb-1">Mobile</label>
                  <p className="font-bold text-gray-900 text-sm">+91 9876543210</p>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
