import React, { useState } from 'react';
import { CartItem } from '../../components/ui/CartDrawer';

interface CartAndCheckoutProps {
  cartItems: CartItem[];
  onUpdateQuantity: (productId: number, qty: number) => void;
  onRemoveItem: (productId: number) => void;
  onBackToShop: () => void;
}

export const CartAndCheckout: React.FC<CartAndCheckoutProps> = ({
  cartItems,
  onUpdateQuantity,
  onRemoveItem,
  onBackToShop,
}) => {
  const [couponCode, setCouponCode] = useState('');
  const [discountAmount, setDiscountAmount] = useState(0);
  const [couponMessage, setCouponMessage] = useState<string | null>(null);
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState<'RAZORPAY' | 'COD'>('RAZORPAY');

  const [shippingAddress, setShippingAddress] = useState({
    fullName: 'Rahul Sharma',
    mobile: '9876543210',
    addressLine1: 'Flat 402, Royal Palms',
    city: 'Mumbai',
    state: 'Maharashtra',
    pincode: '400001',
  });

  const rawSubtotal = cartItems.reduce((sum, item) => sum + item.product.basePrice * item.quantity, 0);
  const shippingFee = rawSubtotal > 499 ? 0 : 49;
  const finalTotal = Math.max(0, rawSubtotal - discountAmount + shippingFee);

  const handleApplyCoupon = (e: React.FormEvent) => {
    e.preventDefault();
    if (couponCode.trim().toUpperCase() === 'SKFREESHIP') {
      setDiscountAmount(100);
      setCouponMessage('🎉 Coupon SKFREESHIP Applied! ₹100 Discount Granted.');
    } else {
      setDiscountAmount(0);
      setCouponMessage('❌ Invalid Coupon Code. Try SKFREESHIP.');
    }
  };

  const handleRazorpayCheckoutModal = () => {
    const loadScript = () => {
      return new Promise((resolve) => {
        if ((window as any).Razorpay) {
          resolve(true);
          return;
        }
        const script = document.createElement('script');
        script.src = 'https://checkout.razorpay.com/v1/checkout.js';
        script.onload = () => resolve(true);
        script.onerror = () => resolve(false);
        document.body.appendChild(script);
      });
    };

    loadScript().then((res) => {
      if (!res) {
        alert('Razorpay Payment Gateway SDK failed to load.');
        return;
      }

      const options = {
        key: 'rzp_test_skbazaarhub123',
        amount: finalTotal * 100, // in paise
        currency: 'INR',
        name: 'SK BazaarHub Multi-Vendor',
        description: `Order Checkout (${cartItems.length} items)`,
        handler: function (response: any) {
          alert(`🎉 Payment Authorized & Order Placed Successfully!\nRazorpay Payment ID: ${response.razorpay_payment_id}`);
          onBackToShop();
        },
        prefill: {
          name: shippingAddress.fullName,
          contact: shippingAddress.mobile,
        },
        theme: {
          color: '#5B35E8',
        },
      };

      const rzp = new (window as any).Razorpay(options);
      rzp.open();
    });
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="flex items-center space-x-2 mb-8">
        <button onClick={onBackToShop} className="text-gray-400 font-bold hover:text-brand-500 text-sm">
          ← Shop
        </button>
        <span className="text-gray-300">/</span>
        <h1 className="text-2xl font-black text-gray-900 tracking-tight">Express Checkout</h1>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Left Column: Address & Items */}
        <div className="lg:col-span-2 space-y-6">
          {/* Shipping Address Manager */}
          <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
            <h2 className="font-extrabold text-base text-gray-900 mb-4 flex items-center space-x-2">
              <span>📍</span>
              <span>1. Shipping Address</span>
            </h2>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-xs">
              <div>
                <label className="block text-gray-400 font-bold uppercase mb-1">Full Name</label>
                <input
                  type="text"
                  value={shippingAddress.fullName}
                  onChange={(e) => setShippingAddress({ ...shippingAddress, fullName: e.target.value })}
                  className="w-full border rounded-xl p-2.5 font-medium"
                />
              </div>
              <div>
                <label className="block text-gray-400 font-bold uppercase mb-1">Mobile</label>
                <input
                  type="text"
                  value={shippingAddress.mobile}
                  onChange={(e) => setShippingAddress({ ...shippingAddress, mobile: e.target.value })}
                  className="w-full border rounded-xl p-2.5 font-medium"
                />
              </div>
              <div className="md:col-span-2">
                <label className="block text-gray-400 font-bold uppercase mb-1">Address Line</label>
                <input
                  type="text"
                  value={shippingAddress.addressLine1}
                  onChange={(e) => setShippingAddress({ ...shippingAddress, addressLine1: e.target.value })}
                  className="w-full border rounded-xl p-2.5 font-medium"
                />
              </div>
            </div>
          </div>

          {/* Payment Method Selector */}
          <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card">
            <h2 className="font-extrabold text-base text-gray-900 mb-4 flex items-center space-x-2">
              <span>💳</span>
              <span>2. Payment Option</span>
            </h2>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div
                onClick={() => setSelectedPaymentMethod('RAZORPAY')}
                className={`p-4 rounded-2xl border-2 cursor-pointer transition flex items-center space-x-3 ${
                  selectedPaymentMethod === 'RAZORPAY' ? 'border-brand-500 bg-brand-50/50' : 'border-gray-200'
                }`}
              >
                <span className="text-xl">💳</span>
                <div>
                  <h4 className="font-bold text-sm text-gray-900">Razorpay Online (UPI/Cards)</h4>
                  <p className="text-[11px] text-gray-500">Instant HMAC SHA-256 Authorized Checkout</p>
                </div>
              </div>

              <div
                onClick={() => setSelectedPaymentMethod('COD')}
                className={`p-4 rounded-2xl border-2 cursor-pointer transition flex items-center space-x-3 ${
                  selectedPaymentMethod === 'COD' ? 'border-brand-500 bg-brand-50/50' : 'border-gray-200'
                }`}
              >
                <span className="text-xl">💵</span>
                <div>
                  <h4 className="font-bold text-sm text-gray-900">Cash on Delivery (COD)</h4>
                  <p className="text-[11px] text-gray-500">Pay cash upon package delivery</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Right Column: Order Summary & Coupon */}
        <div className="space-y-6">
          <div className="bg-white p-6 rounded-3xl border border-gray-100 shadow-card space-y-4">
            <h2 className="font-extrabold text-base text-gray-900 border-b pb-3">Order Summary</h2>

            {/* Coupon Code Input */}
            <form onSubmit={handleApplyCoupon} className="space-y-2">
              <div className="flex">
                <input
                  type="text"
                  placeholder="Coupon Code (SKFREESHIP)"
                  value={couponCode}
                  onChange={(e) => setCouponCode(e.target.value)}
                  className="w-full border border-gray-200 rounded-l-xl px-3 py-2 text-xs font-semibold focus:outline-none uppercase"
                />
                <button
                  type="submit"
                  className="bg-brand-500 text-white font-bold text-xs px-4 rounded-r-xl hover:bg-brand-600 transition"
                >
                  Apply
                </button>
              </div>
              {couponMessage && (
                <p className={`text-[11px] font-bold ${couponMessage.startsWith('🎉') ? 'text-green-600' : 'text-red-500'}`}>
                  {couponMessage}
                </p>
              )}
            </form>

            <div className="space-y-2.5 text-xs border-t pt-3">
              <div className="flex justify-between text-gray-600">
                <span>Items Total ({cartItems.length})</span>
                <span className="font-bold text-gray-900">₹{rawSubtotal.toLocaleString('en-IN')}</span>
              </div>
              {discountAmount > 0 && (
                <div className="flex justify-between text-green-600 font-bold">
                  <span>Coupon Discount</span>
                  <span>-₹{discountAmount.toLocaleString('en-IN')}</span>
                </div>
              )}
              <div className="flex justify-between text-gray-600">
                <span>Shipping Charge</span>
                <span className="font-bold text-gray-900">
                  {shippingFee === 0 ? <span className="text-green-600 uppercase font-extrabold">FREE</span> : `₹${shippingFee}`}
                </span>
              </div>

              <div className="flex justify-between text-base font-black text-gray-900 border-t pt-3">
                <span>Grand Total</span>
                <span className="text-brand-600 text-lg">₹{finalTotal.toLocaleString('en-IN')}</span>
              </div>
            </div>

            <button
              onClick={handleRazorpayCheckoutModal}
              className="w-full bg-gradient-to-r from-brand-500 to-brand-700 text-white font-bold py-3.5 rounded-2xl hover:shadow-lg hover:shadow-brand-500/25 transition active:scale-[0.98] text-sm mt-4"
            >
              Pay & Confirm Order →
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
