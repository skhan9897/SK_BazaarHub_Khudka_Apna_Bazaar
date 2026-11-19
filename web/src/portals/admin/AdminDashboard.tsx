import React, { useState, useEffect } from 'react';
import { api } from '../../api/client';

export const AdminDashboard: React.FC = () => {
  const [kycRequests, setKycRequests] = useState([
    { id: 201, businessName: "Apex Retail Solutions", gstin: "27AAACA0000A1Z5", date: "Nov 19, 2026", status: "SUBMITTED" },
    { id: 202, businessName: "GreenSoul Comfort Ltd", gstin: "07AABCG1234F1Z8", date: "Nov 18, 2026", status: "APPROVED" }
  ]);
  const [analytics, setAnalytics] = useState<any>(null);

  useEffect(() => {
    api.getAdminAnalytics()
      .then((res) => {
        if (res.data && res.data.data) {
          setAnalytics(res.data.data);
        }
      })
      .catch(() => {});
  }, []);

  const handleApproveKyc = (id: number) => {
    setKycRequests(
      kycRequests.map((k) => (k.id === id ? { ...k, status: "APPROVED" } : k))
    );
    alert("Seller KYC Status updated to APPROVED!");
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-8 space-y-8">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-black text-gray-900 tracking-tight">Executive Admin Dashboard</h1>
        <p className="text-gray-500 text-sm mt-1">Platform management, Seller KYC approvals & Commission controls</p>
      </div>

      {/* KPI Overview Grid */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
          <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Platform GMV</span>
          <p className="text-2xl font-black text-brand-600 mt-2">₹4,52,800.00</p>
        </div>
        <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
          <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Verified Sellers</span>
          <p className="text-2xl font-black text-gray-900 mt-2">85</p>
        </div>
        <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
          <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Platform Customers</span>
          <p className="text-2xl font-black text-gray-900 mt-2">12,400</p>
        </div>
        <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
          <span className="text-xs font-extrabold text-gray-400 uppercase tracking-wider">Pending KYC Reviews</span>
          <span className="inline-block mt-2 font-extrabold text-xs px-3 py-1 bg-amber-100 text-amber-800 rounded-full">
            {kycRequests.filter(k => k.status === 'SUBMITTED').length} Sellers
          </span>
        </div>
      </div>

      {/* Seller KYC Verification Table */}
      <div className="bg-white rounded-3xl border border-gray-100 shadow-card overflow-hidden space-y-4 p-6">
        <h2 className="font-extrabold text-lg text-gray-900 border-b pb-3">Seller KYC Verification Queue</h2>
        <table className="w-full text-left text-xs">
          <thead className="bg-surface-bg border-b text-gray-400 uppercase font-extrabold">
            <tr>
              <th className="p-3">Business Name</th>
              <th className="p-3">GSTIN Number</th>
              <th className="p-3">Submission Date</th>
              <th className="p-3">KYC Status</th>
              <th className="p-3">Action</th>
            </tr>
          </thead>
          <tbody className="divide-y">
            {kycRequests.map((seller) => (
              <tr key={seller.id} className="hover:bg-gray-50 font-medium">
                <td className="p-3 font-bold text-gray-900">{seller.businessName}</td>
                <td className="p-3 font-mono">{seller.gstin}</td>
                <td className="p-3 text-gray-500">{seller.date}</td>
                <td className="p-3">
                  <span className={`font-bold px-2.5 py-0.5 rounded-full text-[10px] ${
                    seller.status === 'APPROVED' ? 'bg-green-100 text-green-700' : 'bg-amber-100 text-amber-700'
                  }`}>
                    {seller.status}
                  </span>
                </td>
                <td className="p-3">
                  {seller.status === 'SUBMITTED' ? (
                    <button
                      onClick={() => handleApproveKyc(seller.id)}
                      className="bg-brand-500 text-white font-bold text-xs px-3 py-1.5 rounded-lg hover:bg-brand-600 transition"
                    >
                      Approve KYC ✓
                    </button>
                  ) : (
                    <span className="text-gray-400 text-[11px]">Verified</span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
