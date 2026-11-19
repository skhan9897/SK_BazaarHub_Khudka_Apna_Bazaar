import React, { useState } from 'react';

export const SellerDashboard: React.FC = () => {
  const [activeTab, setActiveTab] = useState<'overview' | 'products' | 'orders' | 'settlements'>('overview');
  const [showAddProductModal, setShowAddProductModal] = useState(false);

  const [products, setProducts] = useState([
    { id: 101, name: "Sony WH-1000XM5 Headphones", price: 24990, stock: 45, status: "APPROVED" },
    { id: 104, name: "Bose SoundLink Flex Speaker", price: 12900, stock: 12, status: "APPROVED" }
  ]);

  const [newProductName, setNewProductName] = useState('');
  const [newProductPrice, setNewProductPrice] = useState('');
  const [newProductStock, setNewProductStock] = useState('50');

  const handleAddProduct = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newProductName || !newProductPrice) return;

    setProducts([
      ...products,
      {
        id: Date.now(),
        name: newProductName,
        price: Number(newProductPrice),
        stock: Number(newProductStock),
        status: "APPROVED"
      }
    ]);
    setShowAddProductModal(false);
    setNewProductName('');
    setNewProductPrice('');
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-8">
        <div>
          <div className="flex items-center space-x-3">
            <h1 className="text-3xl font-black text-gray-900 tracking-tight">Seller Central</h1>
            <span className="bg-green-100 text-green-700 font-extrabold text-xs px-3 py-1 rounded-full border border-green-300">
              KYC APPROVED ✅
            </span>
          </div>
          <p className="text-gray-500 text-sm mt-1">Manage store profile, inventory, sales orders & payouts</p>
        </div>

        <button
          onClick={() => setShowAddProductModal(true)}
          className="bg-brand-500 text-white font-bold text-xs px-5 py-3 rounded-2xl hover:bg-brand-600 transition shadow-lg shadow-brand-500/20"
        >
          + Add New Product
        </button>
      </div>

      {/* Navigation Tabs */}
      <div className="flex space-x-3 border-b mb-8 text-sm font-bold">
        {['overview', 'products', 'orders', 'settlements'].map((tab) => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab as any)}
            className={`capitalize pb-3 px-2 transition border-b-2 ${
              activeTab === tab ? 'border-brand-500 text-brand-600' : 'border-transparent text-gray-400 hover:text-gray-700'
            }`}
          >
            {tab}
          </button>
        ))}
      </div>

      {/* Tab 1: Overview */}
      {activeTab === 'overview' && (
        <div className="space-y-8">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
            <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
              <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Total Sales</span>
              <p className="text-2xl font-black text-gray-900 mt-2">₹1,84,500.00</p>
            </div>
            <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
              <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Total Orders</span>
              <p className="text-2xl font-black text-gray-900 mt-2">156</p>
            </div>
            <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
              <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Active Inventory</span>
              <p className="text-2xl font-black text-gray-900 mt-2">{products.length} Items</p>
            </div>
            <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
              <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Commission Rate</span>
              <p className="text-2xl font-black text-brand-600 mt-2">5.0%</p>
            </div>
          </div>
        </div>
      )}

      {/* Tab 2: Products Manager */}
      {activeTab === 'products' && (
        <div className="bg-white rounded-3xl border border-gray-100 shadow-card overflow-hidden">
          <table className="w-full text-left text-xs">
            <thead className="bg-surface-bg border-b text-gray-400 uppercase font-extrabold">
              <tr>
                <th className="p-4">Product Name</th>
                <th className="p-4">Price</th>
                <th className="p-4">Available Stock</th>
                <th className="p-4">Status</th>
              </tr>
            </thead>
            <tbody className="divide-y">
              {products.map((p) => (
                <tr key={p.id} className="hover:bg-gray-50 font-medium">
                  <td className="p-4 font-bold text-gray-900">{p.name}</td>
                  <td className="p-4 font-extrabold text-brand-600">₹{p.price.toLocaleString('en-IN')}</td>
                  <td className="p-4 font-bold">{p.stock} units</td>
                  <td className="p-4">
                    <span className="bg-green-100 text-green-700 font-bold px-2.5 py-0.5 rounded-full text-[10px]">
                      {p.status}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Modal: Add Product */}
      {showAddProductModal && (
        <div className="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-3xl max-w-md w-full p-6 shadow-2xl space-y-4">
            <div className="flex justify-between items-center border-b pb-3">
              <h3 className="font-extrabold text-lg text-gray-900">Add New Product</h3>
              <button onClick={() => setShowAddProductModal(false)} className="text-gray-400 font-bold">✕</button>
            </div>

            <form onSubmit={handleAddProduct} className="space-y-3 text-xs">
              <div>
                <label className="block text-gray-500 font-bold mb-1">Product Title</label>
                <input
                  type="text"
                  required
                  value={newProductName}
                  onChange={(e) => setNewProductName(e.target.value)}
                  placeholder="e.g. Wireless Bluetooth Earbuds"
                  className="w-full border rounded-xl p-2.5 font-medium"
                />
              </div>

              <div>
                <label className="block text-gray-500 font-bold mb-1">Base Selling Price (₹)</label>
                <input
                  type="number"
                  required
                  value={newProductPrice}
                  onChange={(e) => setNewProductPrice(e.target.value)}
                  placeholder="e.g. 2999"
                  className="w-full border rounded-xl p-2.5 font-medium"
                />
              </div>

              <div>
                <label className="block text-gray-500 font-bold mb-1">Initial Stock Quantity</label>
                <input
                  type="number"
                  required
                  value={newProductStock}
                  onChange={(e) => setNewProductStock(e.target.value)}
                  className="w-full border rounded-xl p-2.5 font-medium"
                />
              </div>

              <button
                type="submit"
                className="w-full bg-brand-500 text-white font-bold py-3 rounded-xl hover:bg-brand-600 transition text-xs mt-2"
              >
                Save & Publish Product →
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
